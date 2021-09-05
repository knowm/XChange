package nostro.xchange.binance.sync;

import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class InitSyncTask implements Callable<SyncTaskDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(InitSyncTask.class);
    
    private final BinanceTradeService service;
    private final CurrencyPair pair;

    public InitSyncTask(BinanceTradeService service, CurrencyPair pair) {
        this.service = service;
        this.pair = pair;
    }
    
    @Override
    public SyncTaskDocument call() throws Exception {
        LOG.info("Starting InitSyncTask(symbol={})", pair);
        
        BinanceOrder order = getOpenOrders().stream()
                .min(Comparator.comparing(BinanceOrder::getTime))
                .orElse(null);
        
        if (order == null) {
            LOG.info("No open orders found, querying for the last");
            order = getOrder();
        }
        
        if  (order == null) {
            LOG.info("No orders found, cannot init sync");
            return null;
        }
        
        BinanceTrade trade = getTrade(order.time);
        if (trade == null) {
            trade = getTrade(null);
        }
        
        if (trade == null) {
            LOG.info("No trades found, cannot init sync");
            return null;
        }
        
        LOG.info("InitSyncTask(symbol={}) finished: orderId={}, tradeId={}", pair, order.orderId, trade.id);
        return new SyncTaskDocument(order.orderId, trade.id);
    }

    private List<BinanceOrder> getOpenOrders() throws IOException {
        try {
            return service.openOrders(pair);
        } catch (Throwable th) {
            LOG.error("Error while querying open orders", th);
            throw th;
        }
    }

    private BinanceOrder getOrder() throws IOException {
        try {
            List<BinanceOrder> orders = service.allOrders(pair, null, 1);
            return !orders.isEmpty() ? orders.get(0) : null;
        } catch (Throwable th) {
            LOG.error("Error while querying orders", th);
            throw th;
        }
    }

    private BinanceTrade getTrade(Long startTime) throws IOException {
        try {
            List<BinanceTrade> trades = service.myTrades(pair, 1, startTime, null, null);
            return !trades.isEmpty() ? trades.get(0) : null;
        } catch (Throwable th) {
            LOG.error("Error while querying trades", th);
            throw th;
        }
    }
}
