package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.sync.SyncTaskDocument;
import nostro.xchange.sync.SyncTask;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class BinanceFuturesInitSyncTask extends SyncTask<SyncTaskDocument, BinanceFuturesSyncService> {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesInitSyncTask.class);

    private final CurrencyPair pair;

    public BinanceFuturesInitSyncTask(BinanceFuturesSyncService syncService, CurrencyPair pair) {
        super(syncService);
        this.pair = pair;
    }

    @Override
    public SyncTaskDocument call() throws Exception {
        LOG.info("Starting task (symbol={})", pair);

        List<BinanceFuturesOrder> openOrders = getSyncService().getOpenOrders(pair);
        BinanceFuturesOrder order = openOrders
                .stream()
                .min(Comparator.comparing(BinanceFuturesOrder::getTime))
                .orElse(null);

        if (order == null) {
            LOG.info("No open orders found, querying for the last");
            order = getSyncService().getLastOrder(pair);
        }

        if  (order == null) {
            LOG.info("No orders found, cannot init sync");
            return null;
        }

        BinanceFuturesTrade trade = getSyncService().getFirstTrade(pair, order.time);
        if (trade == null) {
            trade = getSyncService().getFirstTrade(pair, null);
        }

        if (trade == null) {
            LOG.info("No trades found, cannot init sync");
            return null;
        }

        LOG.info("Finished sync");
        return new SyncTaskDocument(order.orderId, trade.id);
    }
}
