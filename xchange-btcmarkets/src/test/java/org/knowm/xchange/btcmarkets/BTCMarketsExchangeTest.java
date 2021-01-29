package org.knowm.xchange.btcmarkets;

import org.knowm.xchange.btcmarkets.service.BTCMarketsTestSupport;
// Note:
//    I tried my best to get powermock to work after adding a logback.xml file to suppress verbose
// logging but there are some Java 9* issues
//    arising with powermock that I just cannot resolve. Therefore I decided to comment out all the
// powermock code in this module since it's the
//    only module using powermock. The dependencies have also been removed from the project.
// Hopefully someone will upgrade all these test to use
//    Mokito.ange.btcmarkets.service.BTCMarketsTestSupport;

public class BTCMarketsExchangeTest extends BTCMarketsTestSupport {

  //  private BTCMarketsExchange exchange;
  //  private ExchangeSpecification exchangeSpecification;
  //
  //  @Before
  //  public void setUp() throws Exception {
  //    exchange =
  //        (BTCMarketsExchange)
  //
  // ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
  //    exchangeSpecification = new ExchangeSpecification(BTCMarketsExchange.class);
  //  }
  //
  //  @Test
  //  public void shouldApplyDefaultSpecification() {
  //
  //    // when
  //    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
  //
  //    // then
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(exchange.getTradeService()).isNull();
  //    assertThat(exchange.getAccountService()).isNull();
  //  }
  //
  //  @Test
  //  public void shouldApplyDefaultSpecificationWithKeys() {
  //    // given
  //    exchangeSpecification = exchange.getDefaultExchangeSpecification();
  //    exchangeSpecification.getExchangeSpecificParameters();
  //    exchangeSpecification.setApiKey(SPECIFICATION_API_KEY);
  //    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);
  //
  //    // when
  //    exchange.applySpecification(exchangeSpecification);
  //
  //    // then
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(
  //            (BTCMarketsExchange) Whitebox.getInternalState(exchange.getTradeService(),
  // "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getAccountService(), "exchange"))
  //        .isEqualTo(exchange);
  //  }
  //
  //  @Test
  //  public void shouldApplySpecificationWithKeys() {
  //    // given
  //    exchangeSpecification.getExchangeSpecificParameters();
  //    exchangeSpecification.setApiKey(SPECIFICATION_API_KEY);
  //    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);
  //
  //    // when
  //    exchange.applySpecification(exchangeSpecification);
  //
  //    // then
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(
  //            (BTCMarketsExchange) Whitebox.getInternalState(exchange.getTradeService(),
  // "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getAccountService(), "exchange"))
  //        .isEqualTo(exchange);
  //  }
  //
  //  @Test
  //  public void shouldApplySpecificationWithApiKeyOnly() {
  //    // given
  //    exchangeSpecification.setApiKey(SPECIFICATION_API_KEY);
  //
  //    // when
  //    exchange.applySpecification(exchangeSpecification);
  //
  //    // then
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(exchange.getTradeService()).isNull();
  //    assertThat(exchange.getAccountService()).isNull();
  //  }
  //
  //  @Test
  //  public void shouldApplySpecificationWithSecretKeyOnly() {
  //    // given
  //    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);
  //
  //    // when
  //    exchange.applySpecification(exchangeSpecification);
  //
  //    // then
  //    assertThat(
  //            (BTCMarketsExchange)
  //                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
  //        .isEqualTo(exchange);
  //    assertThat(exchange.getTradeService()).isNull();
  //    assertThat(exchange.getAccountService()).isNull();
  //  }
  //
  //  @Test
  //  public void shouldCreateDefaultExchangeSpecification() {
  //    // when
  //    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
  //
  //    // then
  //    assertThat(specification.getExchangeClassName())
  //        .isEqualTo(BTCMarketsExchange.class.getCanonicalName());
  //    assertThat(specification.getExchangeName()).isEqualTo("BTCMarkets");
  //    assertThat(specification.getSslUri()).isEqualTo("https://api.btcmarkets.net");
  //    assertThat(specification.getHost()).isEqualTo("btcmarkets.net");
  //    assertThat(specification.getPort()).isEqualTo(80);
  //    assertThat(specification.getApiKey()).isNull();
  //    assertThat(specification.getSecretKey()).isNull();
  //  }
}
