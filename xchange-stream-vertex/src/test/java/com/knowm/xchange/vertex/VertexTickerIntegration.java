package com.knowm.xchange.vertex;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexTickerIntegration {

  private static final Logger logger = LoggerFactory.getLogger(VertexTickerIntegration.class);


  @Test
  public void subscribesToTicker() throws InterruptedException {
    VertexStreamingExchange exchange = new VertexStreamingExchange();
    ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, false);
    exchange.applySpecification(spec);
    StreamingMarketDataService streamingMarketDataService = exchange.getStreamingMarketDataService();

    Observable<Ticker> ticker = streamingMarketDataService.getTicker(new CurrencyPair("BTC-PERP", "USDC"));

    assertTrue(exchange.connect().blockingAwait(10, TimeUnit.SECONDS));

    CountDownLatch ticks = new CountDownLatch(1);
    Disposable subscription = ticker.subscribe((tick) -> {
      logger.info("Tick: " + tick);
      ticks.countDown();
    }, (error) -> logger.error("Ticker error", error));
    try {

      assertTrue(ticks.await(30, TimeUnit.SECONDS));
    } finally {
      subscription.dispose();
      assertTrue(exchange.disconnect().blockingAwait(10, TimeUnit.SECONDS));
    }
  }

}
