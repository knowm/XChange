package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexManualExample {

    private static final Logger LOG = LoggerFactory.getLogger(BitmexManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitmexStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

        final BitmexStreamingMarketDataService streamingMarketDataService = (BitmexStreamingMarketDataService) exchange.getStreamingMarketDataService();

        CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
        streamingMarketDataService.getOrderBook(xbtUsd).subscribe(orderBook -> {
            if (!orderBook.getAsks().isEmpty()) {
                LOG.info("First ask: {}", orderBook.getAsks().get(0));
            }
            if (!orderBook.getBids().isEmpty()) {
                LOG.info("First bid: {}", orderBook.getBids().get(0));
            }
        }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

        streamingMarketDataService.getRawTicker(xbtUsd).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        streamingMarketDataService.getTicker(xbtUsd).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        exchange.getStreamingMarketDataService().getTrades(xbtUsd)
                .subscribe(trade -> LOG.info("TRADE: {}", trade),
                        throwable -> LOG.error("ERROR in getting trades: ", throwable));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exchange.disconnect().blockingAwait();
    }
}
