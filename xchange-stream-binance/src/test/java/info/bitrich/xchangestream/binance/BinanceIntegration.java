package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.BinanceStreamingExchange.USE_HIGHER_UPDATE_FREQUENCY;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

public class BinanceIntegration {

  @Test
  public void channelCreateUrlTest() {
    BinanceStreamingExchange exchange =
        (BinanceStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class);
    ProductSubscription.ProductSubscriptionBuilder builder = ProductSubscription.create();
    builder.addTicker(CurrencyPair.BTC_USD).addTicker(CurrencyPair.DASH_BTC);
    String buildSubscriptionStreams = exchange.buildSubscriptionStreams(builder.build());
    Assert.assertEquals("btcusd@ticker/dashbtc@ticker", buildSubscriptionStreams);

    ProductSubscription.ProductSubscriptionBuilder builder2 = ProductSubscription.create();
    builder2
        .addTicker(CurrencyPair.BTC_USD)
        .addTicker(CurrencyPair.DASH_BTC)
        .addOrderbook(CurrencyPair.ETH_BTC);
    String buildSubscriptionStreams2 = exchange.buildSubscriptionStreams(builder2.build());
    Assert.assertEquals("btcusd@ticker/dashbtc@ticker/ethbtc@depth", buildSubscriptionStreams2);
  }

  @Test
  public void channelCreateUrlWithUpdateFrequencyTest() {
    ProductSubscription.ProductSubscriptionBuilder builder = ProductSubscription.create();
    builder
        .addTicker(CurrencyPair.BTC_USD)
        .addTicker(CurrencyPair.DASH_BTC)
        .addOrderbook(CurrencyPair.ETH_BTC);
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(BinanceStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(USE_HIGHER_UPDATE_FREQUENCY, true);
    BinanceStreamingExchange exchange =
        (BinanceStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    String buildSubscriptionStreams = exchange.buildSubscriptionStreams(builder.build());
    Assert.assertEquals(
        "btcusd@ticker/dashbtc@ticker/ethbtc@depth@100ms", buildSubscriptionStreams);
  }
}
