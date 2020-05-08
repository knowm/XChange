package info.bitrich.xchangestream.okcoin;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 17.11.17. */
public class OkExManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(OkExManualExample.class);

  public static void main(String[] args) {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(OkExStreamingExchange.class.getName());
    exchange.connect().blockingAwait();

    CurrencyPair btcUsdt = new CurrencyPair(new Currency("BTC"), new Currency("USDT"));
    exchange
        .getStreamingMarketDataService()
        .getOrderBook(btcUsdt)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTicker(btcUsdt)
        .subscribe(
            ticker -> {
              LOG.info("TICKER: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(btcUsdt)
        .subscribe(
            trade -> {
              LOG.info("TRADE: {}", trade);
            },
            throwable -> LOG.error("ERROR in getting trades: ", throwable));

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
