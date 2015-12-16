package com.xeiam.xchange.bleutrade;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeMarketDataService;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeServiceTestSupport;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.SynchronizedValueFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeExchangeTest extends BleutradeServiceTestSupport {
  private BleutradeExchange exchange;
  private ExchangeSpecification exchangeSpecification;

  @Before
  public void setUp() throws Exception {
    exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchangeSpecification = new ExchangeSpecification(BleutradeExchange.class);
  }

  @Test
  public void shouldApplyDefaultSpecification() throws Exception {
    // when
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplyDefaultSpecificationWithKeys() throws Exception {
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
  public void shouldApplySpecificationWithKeys() throws Exception {
    // given
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
  public void shouldApplySpecificationWithApiKeyOnly() throws Exception {
    // given
    exchangeSpecification.setApiKey("apiKey");

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithSecretKeyOnly() throws Exception {
    // given
    exchangeSpecification.setSecretKey("secretKey");

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test(expected = NullPointerException.class)
  public void shouldFailWhenApplyNullSpecification() throws Exception {
    // when
    exchange.applySpecification(null);

    // then
    fail("BTCMarketsExchange should throw NullPointerException when tries to apply null specification");
  }

  @Test
  public void shouldCreateDefaultExchangeSpecification() throws Exception {
    // when
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();

    // then
    assertThat(specification.getExchangeClassName()).isEqualTo(BleutradeExchange.class.getCanonicalName());
    assertThat(specification.getExchangeName()).isEqualTo("Bleutrade");
    assertThat(specification.getSslUri()).isEqualTo("https://bleutrade.com/api/");
    assertThat(specification.getHost()).isEqualTo("bleutrade.com");
    assertThat(specification.getPort()).isEqualTo(80);
    assertThat(specification.getApiKey()).isNull();
    assertThat(specification.getSecretKey()).isNull();
  }

  @Test
  public void shouldCreateNonceFactory() throws Exception {
    // when
    SynchronizedValueFactory factory = exchange.getNonceFactory();

    // then
    assertThat(factory).isNotNull();
    assertThat(factory instanceof AtomicLongIncrementalTime2013NonceFactory).isTrue();
  }

  @Test
  public void shouldMakeRemoteInit() throws Exception {
    // given
    List<BleutradeCurrency> currenciesStub = Arrays.asList(
        createBleutradeCurrency("BTC", "Bitcoin", 2, new BigDecimal("0.00080000"), true, "BITCOIN"),
        createBleutradeCurrency("LTC", "Litecoin", 4, new BigDecimal("0.02000000"), true, "BITCOIN")
    );

    List<BleutradeMarket> marketsStub = Arrays.asList(
        createBleutradeMarket("DOGE", "BTC", "Dogecoin", "Bitcoin", new BigDecimal("0.10000000"), "DOGE_BTC", true),
        createBleutradeMarket("BLEU", "BTC", "Bleutrade Share", "Bitcoin", new BigDecimal("0.00000001"), "BLEU_BTC", true)
    );

    BleutradeMarketDataService pollingMarketDataServiceMock = mock(BleutradeMarketDataService.class);
    PowerMockito.when(pollingMarketDataServiceMock.getBleutradeCurrencies()).thenReturn(currenciesStub);
    PowerMockito.when(pollingMarketDataServiceMock.getBleutradeMarkets()).thenReturn(marketsStub);

    Whitebox.setInternalState(exchange, "pollingMarketDataService", pollingMarketDataServiceMock);

    // when
    exchange.remoteInit();

    // then
    assertThat(exchange.getMetaData().getCurrencyMetaDataMap()).hasSize(2);
    assertThat(exchange.getMetaData().getCurrencyMetaDataMap().get(Currency.BTC).scale).isEqualTo(8);
    assertThat(exchange.getMetaData().getCurrencyMetaDataMap().get(Currency.LTC).scale).isEqualTo(8);
    assertThat(exchange.getMetaData().getMarketMetaDataMap()).hasSize(2);
    assertThat(exchange.getMetaData().getMarketMetaDataMap().get(CurrencyPair.DOGE_BTC).toString()).isEqualTo("MarketMetaData{tradingFee=0.00499375, minimumAmount=0.10000000, priceScale=8}");
    assertThat(exchange.getMetaData().getMarketMetaDataMap().get(new CurrencyPair("BLEU", "BTC")).toString()).isEqualTo("MarketMetaData{tradingFee=0.00499375, minimumAmount=1E-8, priceScale=8}");
  }
}
