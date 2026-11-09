package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.BinanceStreamingExchange.USE_HIGHER_UPDATE_FREQUENCY;
import static info.bitrich.xchangestream.binance.BinanceStreamingExchange.USE_REALTIME_BOOK_TICKER;
import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

class BinanceUsIntegration {

  @Test
  void channelCreateUrlTest() {
    BinanceUsStreamingExchange exchange =
        (BinanceUsStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(BinanceUsStreamingExchange.class);
    ProductSubscription.ProductSubscriptionBuilder builder = ProductSubscription.create();
    builder.addTicker(CurrencyPair.BTC_USD).addTicker(CurrencyPair.DASH_BTC);
    String buildSubscriptionStreams = exchange.buildSubscriptionStreams(builder.build());
    assertThat(buildSubscriptionStreams).isEqualTo("btcusd@ticker/dashbtc@ticker");

    ProductSubscription.ProductSubscriptionBuilder builder2 = ProductSubscription.create();
    builder2
        .addTicker(CurrencyPair.BTC_USD)
        .addTicker(CurrencyPair.DASH_BTC)
        .addOrderbook(CurrencyPair.ETH_BTC);
    String buildSubscriptionStreams2 = exchange.buildSubscriptionStreams(builder2.build());
    assertThat(buildSubscriptionStreams2).isEqualTo("btcusd@ticker/dashbtc@ticker/ethbtc@depth");
  }

  @Test
  void channelCreateUrlWithUpdateFrequencyTest() {
    ProductSubscription.ProductSubscriptionBuilder builder = ProductSubscription.create();
    builder
        .addTicker(CurrencyPair.BTC_USD)
        .addTicker(CurrencyPair.DASH_BTC)
        .addOrderbook(CurrencyPair.ETH_BTC);
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(BinanceUsStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(USE_HIGHER_UPDATE_FREQUENCY, true);
    BinanceUsStreamingExchange exchange =
        (BinanceUsStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    String buildSubscriptionStreams = exchange.buildSubscriptionStreams(builder.build());
    assertThat(buildSubscriptionStreams)
        .isEqualTo("btcusd@ticker/dashbtc@ticker/ethbtc@depth@100ms");
  }

  @Test
  void channelCreateUrlWithRealtimeBookTickerTest() {
    ProductSubscription.ProductSubscriptionBuilder builder = ProductSubscription.create();
    builder
        .addTicker(CurrencyPair.BTC_USD)
        .addTicker(CurrencyPair.DASH_BTC)
        .addOrderbook(CurrencyPair.ETH_BTC);
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(BinanceUsStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(USE_REALTIME_BOOK_TICKER, true);
    BinanceUsStreamingExchange exchange =
        (BinanceUsStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    String buildSubscriptionStreams = exchange.buildSubscriptionStreams(builder.build());
    assertThat(buildSubscriptionStreams)
        .isEqualTo("btcusd@bookTicker/dashbtc@bookTicker/ethbtc@depth");
  }
}
