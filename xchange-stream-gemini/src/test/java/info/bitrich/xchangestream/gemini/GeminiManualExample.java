package info.bitrich.xchangestream.gemini;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(GeminiManualExample.class);

  public static void main(String[] args) {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(GeminiStreamingExchange.class.getName());
    exchange.connect().blockingAwait();

    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_USD)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.BTC_USD)
        .subscribe(
            trade -> {
              LOG.info("TRADE: {}", trade);
            },
            throwable -> LOG.error("ERROR in getting trades: ", throwable));
  }
}
