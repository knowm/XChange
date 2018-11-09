package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.bitmex.BitmexPrompt;
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

        exchange.messageDelay().subscribe(delay -> LOG.info("Message delay: " + delay));

        final BitmexStreamingMarketDataService streamingMarketDataService = (BitmexStreamingMarketDataService) exchange.getStreamingMarketDataService();

        streamingMarketDataService.getIndex(CurrencyPair.XBT_USD).subscribe(order -> {
            LOG.info("XBT Index: {}", order);
        });

        CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
        streamingMarketDataService.getOrderBook(xbtUsd).subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
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

        // Quarterly Contract
        streamingMarketDataService.getOrderBook(xbtUsd, BitmexPrompt.QUARTERLY).subscribe(orderBook -> {
            LOG.info("Quarterly Contract First ask: {}", orderBook.getAsks().get(0));
            LOG.info("Quarterly Contract First bid: {}", orderBook.getBids().get(0));
        }, throwable -> LOG.error("ERROR in getting Quarterly Contract order book: ", throwable));

        streamingMarketDataService.getTicker(xbtUsd, BitmexPrompt.QUARTERLY).subscribe(ticker -> {
            LOG.info("Quarterly Contract TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting Quarterly Contract ticker: ", throwable));

        exchange.getStreamingMarketDataService().getTrades(xbtUsd, BitmexPrompt.QUARTERLY)
                .subscribe(trade -> LOG.info("Quarterly Contract TRADE: {}", trade),
                        throwable -> LOG.error("ERROR in getting Quarterly Contract trades: ", throwable));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
