package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.BinanceStreamingExchange.USE_REALTIME_BOOK_TICKER;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * This is a useful test for profiling behaviour of the realtime book ticker stream under load. Run
 * this with a profiler to ensure that processing is efficient and free of memory leaks
 */
public class BinanceBookTickerRealtimeExample {

  public static void main(String[] args) throws InterruptedException {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceStreamingExchange.class);
    exchangeSpecification.setShouldLoadRemoteMetaData(true);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_REALTIME_BOOK_TICKER, true);
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    ProductSubscription subscription =
        exchange.getExchangeSymbols().stream()
            .limit(50)
            .reduce(
                ProductSubscription.create(),
                ProductSubscription.ProductSubscriptionBuilder::addTicker,
                (productSubscriptionBuilder, productSubscriptionBuilder2) -> {
                  throw new UnsupportedOperationException();
                })
            .build();
    exchange.connect(subscription).blockingAwait();
    exchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.LTC_BTC)
        .forEach(System.out::println);
    Thread.sleep(Long.MAX_VALUE);
  }
}
