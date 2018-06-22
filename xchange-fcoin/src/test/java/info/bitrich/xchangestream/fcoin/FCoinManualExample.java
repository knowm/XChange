package info.bitrich.xchangestream.fcoin;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FCoinManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(FCoinManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(FCoinStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USDT).subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0).getLimitPrice());
            LOG.info("First bid: {}", orderBook.getBids().get(0).getLimitPrice());
        }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

