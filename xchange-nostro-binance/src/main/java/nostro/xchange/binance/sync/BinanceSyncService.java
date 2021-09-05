package nostro.xchange.binance.sync;

import nostro.xchange.persistence.TransactionFactory;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class BinanceSyncService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceSyncService.class);
    
    private static final long SYNC_DELAY_SECONDS = 300;
    
    private final TransactionFactory txFactory;
    private final BinanceTradeService tradeService;
    private final ScheduledExecutorService executor;

    private ScheduledFuture<?> scheduled = null;

    public BinanceSyncService(TransactionFactory txFactory, BinanceTradeService tradeService) {
        this.txFactory = txFactory;
        this.tradeService = tradeService;
        this.executor = Executors.newScheduledThreadPool(1);

        ((ScheduledThreadPoolExecutor) executor).setRemoveOnCancelPolicy(true);
    }

    public synchronized void init() throws Exception {
        LOG.info("BinanceSyncService starting initialization");
        doSync();
        
        if (scheduled == null) {
            scheduled = executor.scheduleWithFixedDelay(this::doSync2, SYNC_DELAY_SECONDS, SYNC_DELAY_SECONDS, TimeUnit.SECONDS);
            LOG.info("BinanceSyncService initialized, sync task scheduled with delay={} sec", SYNC_DELAY_SECONDS);
        }
    }

    public synchronized void connectionStateChanged(boolean connected) {
        if (connected) {
            if (scheduled == null) {
                scheduled = executor.scheduleWithFixedDelay(this::doSync2, 0, SYNC_DELAY_SECONDS, TimeUnit.SECONDS);
                LOG.info("BinanceSyncService connected, sync task scheduled");
            }
        } else {
            if (scheduled != null) {
                scheduled.cancel(false);
                scheduled = null;
                LOG.info("BinanceSyncService disconnected, sync task canceled");
            }
        }
    }
    
    private void doSync() throws Exception {
        try {
            new SyncAllTask(txFactory, tradeService).call();
        } catch (Throwable th) {
            LOG.error("Sync failed", th);
            throw th;
        }
    }

    private void doSync2() {
        try {
            doSync();
        } catch (Exception e) {
            // do nothing, error logged inside "executeSync"
        }
    }
}
