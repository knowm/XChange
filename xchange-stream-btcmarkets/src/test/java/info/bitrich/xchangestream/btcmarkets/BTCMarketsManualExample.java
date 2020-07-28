package info.bitrich.xchangestream.btcmarkets;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTCMarketsManualExample {
  private static final Logger logger = LoggerFactory.getLogger(BTCMarketsManualExample.class);

  public static void main(String[] args) {

    ExchangeSpecification defaultExchangeSpecification =
        new ExchangeSpecification(BTCMarketsStreamingExchange.class);

    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
    exchange.connect().blockingAwait();

    Disposable btcOrderBookDisposable =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_AUD)
            .forEach(
                orderBook -> {
                  logger.info("First btc ask: {}", orderBook.getAsks().get(0));
                  logger.info("First btc bid: {}", orderBook.getBids().get(0));
                });

    Disposable ethOrderBookDisposable =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.ETH_AUD)
            .forEach(
                orderBook -> {
                  logger.info("First eth ask: {}", orderBook.getAsks().get(0));
                  logger.info("First eth bid: {}", orderBook.getBids().get(0));
                });

    try {
      Thread.sleep(30000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    btcOrderBookDisposable.dispose();
    ethOrderBookDisposable.dispose();
    exchange.disconnect().subscribe(() -> logger.info("Disconnected from the Exchange"));
  }
}
