package org.knowm.xchange.btcmarkets;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.service.BTCMarketsTestSupport;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCMarketsExchangeTest extends BTCMarketsTestSupport {

  private BTCMarketsExchange exchange;
  private ExchangeSpecification exchangeSpecification;

  @Before
  public void setUp() throws Exception {
    exchange =
        (BTCMarketsExchange)
            ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    exchangeSpecification = new ExchangeSpecification(BTCMarketsExchange.class);
  }

  @Test
  public void shouldApplyDefaultSpecification() {

    // when
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    // then
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(exchange.getTradeService()).isNull();
    assertThat(exchange.getAccountService()).isNull();
  }

  @Test
  public void shouldApplyDefaultSpecificationWithKeys() {
    // given
    exchangeSpecification = exchange.getDefaultExchangeSpecification();
    exchangeSpecification
        .getExchangeSpecificParameters()
        .put(BTCMarketsExchange.CURRENCY_PAIR, CurrencyPair.BTC_AUD);
    exchangeSpecification.setApiKey(SPECIFICATION_API_KEY);
    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(
            (BTCMarketsExchange) Whitebox.getInternalState(exchange.getTradeService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getAccountService(), "exchange"))
        .isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithKeys() {
    // given
    exchangeSpecification
        .getExchangeSpecificParameters()
        .put(BTCMarketsExchange.CURRENCY_PAIR, CurrencyPair.BTC_AUD);
    exchangeSpecification.setApiKey(SPECIFICATION_API_KEY);
    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(
            (BTCMarketsExchange) Whitebox.getInternalState(exchange.getTradeService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getAccountService(), "exchange"))
        .isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithApiKeyOnly() {
    // given
    exchangeSpecification.setApiKey(SPECIFICATION_API_KEY);

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(exchange.getTradeService()).isNull();
    assertThat(exchange.getAccountService()).isNull();
  }

  @Test
  public void shouldApplySpecificationWithSecretKeyOnly() {
    // given
    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(
            (BTCMarketsExchange)
                Whitebox.getInternalState(exchange.getMarketDataService(), "exchange"))
        .isEqualTo(exchange);
    assertThat(exchange.getTradeService()).isNull();
    assertThat(exchange.getAccountService()).isNull();
  }

  @Test
  public void shouldCreateDefaultExchangeSpecification() {
    // when
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();

    // then
    assertThat(specification.getExchangeClassName())
        .isEqualTo(BTCMarketsExchange.class.getCanonicalName());
    assertThat(specification.getExchangeName()).isEqualTo("BTCMarkets");
    assertThat(specification.getSslUri()).isEqualTo("https://api.btcmarkets.net");
    assertThat(specification.getHost()).isEqualTo("btcmarkets.net");
    assertThat(specification.getPort()).isEqualTo(80);
    assertThat(specification.getApiKey()).isNull();
    assertThat(specification.getSecretKey()).isNull();
  }

  @Test
  public void shouldCreateNonceFactory() {
    // when
    SynchronizedValueFactory factory = exchange.getNonceFactory();

    // then
    assertThat(factory).isNotNull();
    assertThat(factory instanceof CurrentTimeNonceFactory).isTrue();
  }
}
