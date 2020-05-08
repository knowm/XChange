package info.bitrich.xchangestream.bitflyer;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 14.11.17. */
public class BitflyerManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(BitflyerManualExample.class);

  public static void main(String[] args) {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(BitflyerStreamingExchange.class.getName());
    exchange.connect().blockingAwait();

    // Note that, the receiving first order book snapshot takes several seconds or minutes!
    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_JPY)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.BTC_JPY)
        .subscribe(
            ticker -> {
              LOG.info("TICKER: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.BTC_JPY)
        .subscribe(
            trade -> LOG.info("TRADE: {}", trade),
            throwable -> LOG.error("ERROR in getting trades: ", throwable));

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
