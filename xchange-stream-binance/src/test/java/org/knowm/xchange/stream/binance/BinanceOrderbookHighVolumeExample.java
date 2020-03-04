package org.knowm.xchange.stream.binance;

import org.knowm.xchange.stream.core.ProductSubscription;
import org.knowm.xchange.stream.core.StreamingExchange;
import org.knowm.xchange.stream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

/**
 * This is a useful test for profiling behaviour of the orderbook stream under load. Run this with a
 * profiler to ensure that processing is efficient and free of memory leaks
 */
public class BinanceOrderbookHighVolumeExample {

  public static void main(String[] args) throws InterruptedException {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceStreamingExchange.class);
    exchangeSpecification.setShouldLoadRemoteMetaData(true);
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    ProductSubscription subscription =
        exchange.getExchangeSymbols().stream()
            .limit(50)
            .reduce(
                ProductSubscription.create(),
                ProductSubscription.ProductSubscriptionBuilder::addOrderbook,
                (productSubscriptionBuilder, productSubscriptionBuilder2) -> {
                  throw new UnsupportedOperationException();
                })
            .build();
    exchange.connect(subscription).blockingAwait();
    Thread.sleep(Long.MAX_VALUE);
  }
}
