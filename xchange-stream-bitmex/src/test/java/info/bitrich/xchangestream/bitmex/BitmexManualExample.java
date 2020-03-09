package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexManualExample {

  private static final Logger LOG = LoggerFactory.getLogger(BitmexManualExample.class);

  public static void main(String[] args) {
    BitmexStreamingExchange exchange =
        (BitmexStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(
                BitmexStreamingExchange.class.getName());
    exchange.connect().blockingAwait();

    exchange.messageDelay().subscribe(delay -> LOG.info("Message delay: " + delay));

    final BitmexStreamingMarketDataService streamingMarketDataService =
        (BitmexStreamingMarketDataService) exchange.getStreamingMarketDataService();

    CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
    streamingMarketDataService
        .getOrderBook(xbtUsd)
        .subscribe(
            orderBook -> {
              if (!orderBook.getAsks().isEmpty()) {
                LOG.info("First ask: {}", orderBook.getAsks());
              }
              if (!orderBook.getBids().isEmpty()) {
                LOG.info("First bid: {}", orderBook.getBids());
              }
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    streamingMarketDataService
        .getRawTicker(xbtUsd)
        .subscribe(
            ticker -> {
              LOG.info("TICKER: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    streamingMarketDataService
        .getTicker(xbtUsd)
        .subscribe(
            ticker -> {
              LOG.info("TICKER: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(xbtUsd)
        .subscribe(
            trade -> LOG.info("TRADE: {}", trade),
            throwable -> LOG.error("ERROR in getting trades: ", throwable));

    // BIQUARTERLY Contract
    CurrencyPair xbtUsdBiquarterly =
        exchange.determineActiveContract(
            CurrencyPair.XBT_USD.base.toString(),
            CurrencyPair.XBT_USD.counter.toString(),
            BitmexPrompt.BIQUARTERLY);
    streamingMarketDataService
        .getOrderBook(xbtUsdBiquarterly)
        .subscribe(
            orderBook -> {
              LOG.info("BIQUARTERLY Contract First ask: {}", orderBook.getAsks().get(0));
              LOG.info("BIQUARTERLY Contract First bid: {}", orderBook.getBids().get(0));
            },
            throwable ->
                LOG.error("ERROR in getting BIQUARTERLY Contract order book: ", throwable));

    streamingMarketDataService
        .getTicker(xbtUsdBiquarterly)
        .subscribe(
            ticker -> {
              LOG.info("BIQUARTERLY Contract TICKER: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting BIQUARTERLY Contract ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(xbtUsdBiquarterly)
        .subscribe(
            trade -> LOG.info("BIQUARTERLY Contract TRADE: {}", trade),
            throwable -> LOG.error("ERROR in getting BIQUARTERLY Contract trades: ", throwable));

    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    exchange.disconnect().blockingAwait();
  }
}
