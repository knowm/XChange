package info.bitrich.xchangestream.bitstamp;

import static java.util.concurrent.TimeUnit.MINUTES;

import info.bitrich.xchangestream.bitstamp.v2.BitstampStreamingExchange;
import info.bitrich.xchangestream.bitstamp.v2.BitstampStreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.ConnectableService;
import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitstampManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(BitstampManualExample.class);

  private static final TimedSemaphore rateLimiter = new TimedSemaphore(1, MINUTES, 20);

  private static void rateLimit() {
    try {
      rateLimiter.acquire();
    } catch (InterruptedException e) {
      LOG.warn("Bitstamp connection throttle control has been interrupted");
    }
  }

  public static void main(String[] args) {

    ExchangeSpecification defaultExchangeSpecification =
        new ExchangeSpecification(BitstampStreamingExchange.class);
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) BitstampManualExample::rateLimit);

    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
    exchange.connect().blockingAwait();

    Disposable orderBookDisposable =
        ((BitstampStreamingMarketDataService) exchange.getStreamingMarketDataService())
            .getFullOrderBook(CurrencyPair.BTC_USD)
            .subscribe(
                orderBook -> {
                  LOG.info("First ask: {}", orderBook.getAsks().get(0));
                  LOG.info("First bid: {}", orderBook.getBids().get(0));
                });

    Disposable tradesDisposable =
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USD)
            .subscribe(
                trade -> {
                  LOG.info("Trade {}", trade);
                });

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    orderBookDisposable.dispose();
    tradesDisposable.dispose();
    exchange.disconnect().subscribe(() -> LOG.info("Disconnected from the Exchange"));

    rateLimiter.shutdown();
  }
}
