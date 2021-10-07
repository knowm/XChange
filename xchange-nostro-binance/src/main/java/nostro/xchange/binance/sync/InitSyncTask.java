package nostro.xchange.binance.sync;

import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.concurrent.Callable;

public class InitSyncTask implements Callable<SyncTaskDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(InitSyncTask.class);
    
    private final BinanceSyncService syncService;
    private final CurrencyPair pair;

    public InitSyncTask(BinanceSyncService syncService, CurrencyPair pair) {
        this.syncService = syncService;
        this.pair = pair;
    }
    
    @Override
    public SyncTaskDocument call() throws Exception {
        LOG.info("Starting InitSyncTask(symbol={})", pair);
        
        BinanceOrder order = syncService.getOpenOrders(pair).stream()
                .min(Comparator.comparing(BinanceOrder::getTime))
                .orElse(null);
        
        if (order == null) {
            LOG.info("No open orders found, querying for the last");
            order = syncService.getLastOrder(pair);
        }
        
        if  (order == null) {
            LOG.info("No orders found, cannot init sync");
            return null;
        }
        
        BinanceTrade trade = syncService.getFirstTrade(pair, order.time);
        if (trade == null) {
            trade = syncService.getFirstTrade(pair, null);
        }
        
        if (trade == null) {
            LOG.info("No trades found, cannot init sync");
            return null;
        }
        
        LOG.info("InitSyncTask(symbol={}) finished: orderId={}, tradeId={}", pair, order.orderId, trade.id);
        return new SyncTaskDocument(order.orderId, trade.id);
    }
}
