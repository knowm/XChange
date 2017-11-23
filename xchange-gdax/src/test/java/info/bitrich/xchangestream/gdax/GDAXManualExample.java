package info.bitrich.xchangestream.gdax;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GDAXManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(GDAXManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(GDAXStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

        CurrencyPair[] currencyPairs = new CurrencyPair[] {CurrencyPair.BTC_USD, CurrencyPair.BTC_EUR};
        try {
            ((GDAXStreamingMarketDataService)exchange.getStreamingMarketDataService()).subscribeMultipleCurrencyPairs(currencyPairs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD).subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> LOG.error("ERROR in getting order book: ", throwable));


        exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_EUR).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));


        exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD)
          .subscribe(trade -> {
            LOG.info("TRADE: {}", trade);
        }, throwable -> LOG.error("ERROR in getting trades: ", throwable));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
