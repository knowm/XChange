package nostro.xchange.sync;

import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

public abstract class SyncService {
    private static final Logger LOG = LoggerFactory.getLogger(SyncService.class);

    final TransactionFactory txFactory;
    final NostroStreamingPublisher publisher;

    private final long syncDelay;

    private final ScheduledExecutorService executor;

    private ScheduledFuture<?> scheduled = null;

    public SyncService(TransactionFactory txFactory,
                       NostroStreamingPublisher publisher,
                       long syncDelay) {
        this.txFactory = txFactory;
        this.publisher = publisher;
        this.syncDelay = syncDelay;
        this.executor = Executors.newScheduledThreadPool(1);

        ((ScheduledThreadPoolExecutor) executor).setRemoveOnCancelPolicy(true);
    }

    public TransactionFactory getTXFactory() {
        return txFactory;
    }

    public NostroStreamingPublisher getPublisher() {
        return publisher;
    }

    public synchronized void init() throws Exception {
        LOG.info("starting initialization");
        doSync();

        if (scheduled == null) {
            scheduled = executor.scheduleWithFixedDelay(this::doSync2, syncDelay, syncDelay, TimeUnit.SECONDS);
            LOG.info("initialized, sync task scheduled with delay={} sec", syncDelay);
        }
    }

    public synchronized void connectionStateChanged(boolean connected) {
        if (connected) {
            if (scheduled == null) {
                scheduled = executor.scheduleWithFixedDelay(this::doSync2, 0, syncDelay, TimeUnit.SECONDS);
                LOG.info("connected, sync task scheduled");
            }
        } else {
            if (scheduled != null) {
                scheduled.cancel(false);
                scheduled = null;
                LOG.info("disconnected, sync task canceled");
            }
        }
    }

    public abstract List<Callable<?>> generateTasks();

    private void doSync() throws Exception {
        try {
            LOG.info("Starting sync");
            for (Callable<?> callable : generateTasks()) {
                callable.call();
            }
            LOG.info("Finished sync");
        } catch (Throwable th) {
            LOG.error("Sync failed", th);
            throw th;
        }
    }

    private void doSync2() {
        try {
            doSync();
        } catch (Exception e) {
            // do nothing, error logged inside "doSync"
        }
    }
}
