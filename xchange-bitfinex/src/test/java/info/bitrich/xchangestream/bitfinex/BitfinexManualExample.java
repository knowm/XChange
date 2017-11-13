package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(BitfinexManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitfinexStreamingExchange.class
                .getName());
        exchange.connect().blockingAwait();

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
        }, throwable -> LOG.error("ERROR in getting trade: ", throwable));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
