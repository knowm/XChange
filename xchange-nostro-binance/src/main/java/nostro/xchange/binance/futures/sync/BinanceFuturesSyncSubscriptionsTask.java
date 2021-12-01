package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.sync.SyncTaskDocument;
import nostro.xchange.persistence.SyncTaskEntity;
import nostro.xchange.sync.SyncTask;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BinanceFuturesSyncSubscriptionsTask extends SyncTask<Void, BinanceFuturesSyncService> {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesSyncSubscriptionsTask.class);

    private final Set<String> orders;

    BinanceFuturesSyncSubscriptionsTask(BinanceFuturesSyncService syncService, Set<String> orders) {
        super(syncService);
        this.orders = orders;
    }

    @Override
    public Void call() throws Exception {
        LOG.info("Starting task");
        Set<String> orderSubscriptions = new HashSet<>(orders);

        List<SyncTaskEntity> tasks = getSyncService().getTXFactory().executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest());

        for (SyncTaskEntity task : tasks) {
            final String symbol = task.getSymbol();
            if (orderSubscriptions.remove(task.getSymbol())) {
                LOG.info("Starting sync for symbol={} last sync={}", symbol, task.getTimestamp());
                syncTradingSymbol(symbol, SyncTaskDocument.read(task.getDocument()));
            } else {
                LOG.debug("Ignoring sync for symbol={} last sync={}", symbol, task.getTimestamp());
            }
        }

        for(String symbol : orderSubscriptions) {
            LOG.info("Found new order subscription, starting InitSyncTask for symbol={}", symbol);

            final SyncTaskDocument document = new BinanceFuturesInitSyncTask(getSyncService(), new CurrencyPair(symbol)).call();
            if (document != null) {
                syncTradingSymbol(symbol, document);
            }
        }

        LOG.info("Finished task");
        return null;
    }

    private void syncTradingSymbol(String symbol, SyncTaskDocument document) throws Exception {
        LOG.debug("sync trading symbol={} document={}", symbol, document);
        CurrencyPair pair = new CurrencyPair(symbol);

        new BinanceFuturesOpenOrderSyncTask(getSyncService(), pair, document.getOrderId()).call();
        long orderId = new BinanceFuturesOrderSyncTask(getSyncService(), pair, document.getOrderId()).call();
        long tradeId = new BinanceFuturesTradeSyncTask(getSyncService(), pair, document.getTradeId()).call();
        SyncTaskDocument updated = new SyncTaskDocument(orderId, tradeId);
        if (!updated.equals(document)) {
            getSyncService().getTXFactory().execute(tx ->
                    tx.getSyncTaskRepository().insert(symbol, tx.getTimestamp(), SyncTaskDocument.write(updated)));
        }
    }

}
