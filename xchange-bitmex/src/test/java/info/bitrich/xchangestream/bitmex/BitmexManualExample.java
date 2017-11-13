package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.Currency;
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

    CurrencyPair xbtUsd = new CurrencyPair(new Currency("XBT"), new Currency("USD"));

    exchange.getStreamingMarketDataService().getOrderBook(xbtUsd).subscribe(orderBook -> {
      LOG.info("First ask: {}", orderBook.getAsks().get(0));
      LOG.info("First bid: {}", orderBook.getBids().get(0));
    }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

    ((BitmexStreamingMarketDataService)exchange.getStreamingMarketDataService()).getRawTicker(xbtUsd).subscribe(ticker -> {
      LOG.info("TICKER: {}", ticker);
    }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange.getStreamingMarketDataService().getTicker(xbtUsd).subscribe(ticker -> {
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
  }
}
