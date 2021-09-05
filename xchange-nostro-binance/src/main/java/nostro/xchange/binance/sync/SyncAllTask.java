package nostro.xchange.binance.sync;

import nostro.xchange.persistence.SyncTaskEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.AccountDocument;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class SyncAllTask implements Callable<Void> {
    private static final Logger LOG = LoggerFactory.getLogger(SyncAllTask.class);

    private final TransactionFactory txFactory;
    private final BinanceTradeService service;

    public SyncAllTask(TransactionFactory txFactory, BinanceTradeService service) {
        this.txFactory = txFactory;
        this.service = service;
    }

    @Override
    public Void call() throws Exception {
        AccountDocument account = NostroUtils.readAccountDocument(txFactory.executeAndGet(tx -> tx.getAccountRepository().get()));
        Set<String> orderSubscriptions = new HashSet<>(account.getSubscriptions().getOrders());

        List<SyncTaskEntity> tasks = txFactory.executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest());

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

            SyncTaskDocument document = new InitSyncTask(service, new CurrencyPair(symbol)).call();
            if (document != null) {
                syncTradingSymbol(symbol, document);
            }
        }
        
        return null;
    }

    private void syncTradingSymbol(String symbol, SyncTaskDocument document) throws Exception {
        CurrencyPair pair = new CurrencyPair(symbol);

        new OpenOrderSyncTask(this.txFactory, service, pair, document.getOrderId()).call();
        long orderId = new OrderSyncTask(this.txFactory, service, pair, document.getOrderId()).call();
        long tradeId = new TradeSyncTask(this.txFactory, service, pair, document.getTradeId()).call();

        SyncTaskDocument updated = new SyncTaskDocument(orderId, tradeId);
        if (!updated.equals(document)) {
            txFactory.execute(tx ->
                    tx.getSyncTaskRepository().insert(symbol, tx.getTimestamp(), SyncTaskDocument.write(updated)));
        }
    }
}
