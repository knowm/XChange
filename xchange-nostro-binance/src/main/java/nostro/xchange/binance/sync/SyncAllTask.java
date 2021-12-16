package nostro.xchange.binance.sync;

import nostro.xchange.persistence.SyncTaskEntity;
import nostro.xchange.utils.AccountDocument;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class SyncAllTask implements Callable<Void> {
    private static final Logger LOG = LoggerFactory.getLogger(SyncAllTask.class);
    
    private final BinanceSyncService syncService;

    public SyncAllTask(BinanceSyncService syncService) {
        this.syncService = syncService;
    }

    @Override
    public Void call() throws Exception {
        new BalanceSyncTask(syncService).call();
        
        AccountDocument account = NostroUtils.readAccountDocument(syncService.getTXFactory().executeAndGet(tx -> tx.getAccountRepository().get()));
        Set<String> orderSubscriptions = new HashSet<>(account.getSubscriptions().getOrders());

        List<SyncTaskEntity> tasks = syncService.getTXFactory().executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest());

        for (SyncTaskEntity task : tasks) {
            if (orderSubscriptions.remove(task.getSymbol())) {
                LOG.info("Starting sync for symbol={}, last sync={}", task.getSymbol(), task.getTimestamp());
                syncTradingSymbol(task.getSymbol(), SyncTaskDocument.read(task.getDocument()));
            } else {
                LOG.debug("Ignoring sync for symbol={}, last sync={}", task.getSymbol(), task.getTimestamp());
            }
        }

        for(String symbol : orderSubscriptions) {
            LOG.info("Found new order subscription, starting InitSyncTask for symbol={}", symbol);

            SyncTaskDocument document = new InitSyncTask(syncService, new CurrencyPair(symbol)).call();
            if (document != null) {
                syncTradingSymbol(symbol, document);
            }
        }
        
        return null;
    }

    private void syncTradingSymbol(String symbol, SyncTaskDocument document) throws Exception {
        CurrencyPair pair = new CurrencyPair(symbol);

        new OpenOrderSyncTask(syncService, pair, document.getOrderId()).call();
        long orderId = new OrderSyncTask(syncService, pair, document.getOrderId()).call();
        long tradeId = new TradeSyncTask(syncService, pair, document.getTradeId()).call();

        SyncTaskDocument updated = new SyncTaskDocument(orderId, tradeId);
        if (!updated.equals(document)) {
            syncService.getTXFactory().execute(tx ->
                    tx.getSyncTaskRepository().insert(symbol, tx.getTimestamp(), SyncTaskDocument.write(updated)));
        }
    }
}
