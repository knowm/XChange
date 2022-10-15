package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinanceKlineExample {

  public static void main(String[] args) throws InterruptedException {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceStreamingExchange.class);
    exchangeSpecification.setShouldLoadRemoteMetaData(true);
    BinanceStreamingExchange exchange =
        (BinanceStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    exchange.connect(getKlineSubscription(exchange), getProductSubscription(exchange)).blockingAwait();
    Thread.sleep(Long.MAX_VALUE);
  }

  private static KlineSubscription getKlineSubscription(BinanceStreamingExchange exchange) {
    Set<KlineInterval> klineIntervals = Arrays.stream(KlineInterval.values()).collect(Collectors.toSet());
    Map<CurrencyPair, Set<KlineInterval>> klineSubscriptionMap = exchange.getExchangeSymbols().stream()
        .limit(50)
        .collect(Collectors.toMap(Function.identity(), c-> klineIntervals));

    KlineSubscription klineSubscription = new KlineSubscription(klineSubscriptionMap);
    return klineSubscription;
  }

  private static ProductSubscription getProductSubscription(BinanceStreamingExchange exchange) {
    return exchange.getExchangeSymbols().stream()
        .limit(50)
        .reduce(
            ProductSubscription.create(),
            ProductSubscription.ProductSubscriptionBuilder::addOrderbook,
            (productSubscriptionBuilder, productSubscriptionBuilder2) -> {
              throw new UnsupportedOperationException();
            })
        .build();
  }
}
