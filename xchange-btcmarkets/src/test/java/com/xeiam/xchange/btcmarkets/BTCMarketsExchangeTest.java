package com.xeiam.xchange.btcmarkets;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.dto.BTCMarketsDtoTestSupport;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.SynchronizedValueFactory;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class BTCMarketsExchangeTest extends BTCMarketsDtoTestSupport {

  private BTCMarketsExchange exchange;
  private ExchangeSpecification exchangeSpecification;

  @Before
  public void setUp() throws Exception {
    exchange = (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    exchangeSpecification = new ExchangeSpecification(BTCMarketsExchange.class);
  }

  @Test
  public void shouldApplyDefaultSpecification() {
    // when
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(exchange.getPollingTradeService()).isNull();
    assertThat(exchange.getPollingAccountService()).isNull();
  }

  @Test
  @Ignore("Test case fails")
  public void shouldApplyDefaultSpecificationWithKeys() {
    // when
    exchangeSpecification = exchange.getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey("apiKey");
    exchangeSpecification.setSecretKey("secretKey");

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithKeys() {
    // given
    exchangeSpecification.getExchangeSpecificParameters().put(BTCMarketsExchange.CURRENCY_PAIR, CurrencyPair.BTC_AUD);
    exchangeSpecification.setApiKey("apiKey");
    exchangeSpecification.setSecretKey("secretKey");

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithApiKeyOnly() {
    // given
    exchangeSpecification.setApiKey("apiKey");

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(exchange.getPollingTradeService()).isNull();
    assertThat(exchange.getPollingAccountService()).isNull();
  }

  @Test
  public void shouldApplySpecificationWithSecretKeyOnly() {
    // given
    exchangeSpecification.setSecretKey("secretKey");

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(exchange.getPollingTradeService()).isNull();
    assertThat(exchange.getPollingAccountService()).isNull();
  }


  @Test(expected = NullPointerException.class)
  public void shouldFailWhenApplyNullSpecification() {
    // when
    exchange.applySpecification(null);

    // then
    fail("BTCMarketsExchange should throw NullPointerException when tries to apply null specification");
  }

  @Test
  public void shouldCreateDefaultExchangeSpecification() {
    // when
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();

    // then
    assertThat(specification.getExchangeClassName()).isEqualTo(BTCMarketsExchange.class.getCanonicalName());
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
