package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class BinanceManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.ETH_BTC).subscribe(orderBook -> {
            List<LimitOrder> asks = orderBook.getAsks();
            if (!asks.isEmpty()) {
                LOG.info("First ask: {}", asks.get(0));
            }
            List<LimitOrder> bids = orderBook.getBids();
            if (!bids.isEmpty()) {
                LOG.info("First bid: {}", orderBook.getBids().get(0));
            }
        }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

    }
}
