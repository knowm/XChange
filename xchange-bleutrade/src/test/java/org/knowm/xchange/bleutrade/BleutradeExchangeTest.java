package org.knowm.xchange.bleutrade;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.service.polling.BleutradeMarketDataService;
import org.knowm.xchange.bleutrade.service.polling.BleutradeServiceTestSupport;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.MarketMetaData;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
public class BleutradeExchangeTest extends BleutradeServiceTestSupport {
  private BleutradeExchange exchange;
  private ExchangeSpecification exchangeSpecification;

  @Before
  public void setUp() {
    exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchangeSpecification = new ExchangeSpecification(BleutradeExchange.class);
  }

  @Test
  public void shouldApplyDefaultSpecification() {
    // when
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplyDefaultSpecificationWithKeys() {
    // when
    exchangeSpecification = exchange.getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey("apiKey");
    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithKeys() {
    // given
    exchangeSpecification.setApiKey("apiKey");
    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);

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
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
  }

  @Test
  public void shouldApplySpecificationWithSecretKeyOnly() {
    // given
    exchangeSpecification.setSecretKey(SPECIFICATION_SECRET_KEY);

    // when
    exchange.applySpecification(exchangeSpecification);

    // then
    assertThat(Whitebox.getInternalState(exchange.getPollingMarketDataService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingTradeService(), "exchange")).isEqualTo(exchange);
    assertThat(Whitebox.getInternalState(exchange.getPollingAccountService(), "exchange")).isEqualTo(exchange);
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
    assertThat(specification.getExchangeClassName()).isEqualTo(BleutradeExchange.class.getCanonicalName());
    assertThat(specification.getExchangeName()).isEqualTo("Bleutrade");
    assertThat(specification.getSslUri()).isEqualTo("https://bleutrade.com/api/");
    assertThat(specification.getHost()).isEqualTo("bleutrade.com");
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
    assertThat(factory instanceof AtomicLongIncrementalTime2013NonceFactory).isTrue();
  }

  @Test
  public void shouldMakeRemoteInit() throws IOException {
    // given
    List<BleutradeCurrency> currenciesStub = Arrays.asList(
        createBleutradeCurrency("BTC", "Bitcoin", 2, new BigDecimal("0.00080000"), true, "BITCOIN"),
        createBleutradeCurrency("LTC", "Litecoin", 4, new BigDecimal("0.02000000"), true, "BITCOIN"));

    List<BleutradeMarket> marketsStub = Arrays.asList(
        createBleutradeMarket("DOGE", "BTC", "Dogecoin", "Bitcoin", new BigDecimal("0.10000000"), "DOGE_BTC", true),
        createBleutradeMarket("BLEU", "BTC", "Bleutrade Share", "Bitcoin", new BigDecimal("0.00000001"), "BLEU_BTC", true));

    BleutradeMarketDataService pollingMarketDataServiceMock = mock(BleutradeMarketDataService.class);
    PowerMockito.when(pollingMarketDataServiceMock.getBleutradeCurrencies()).thenReturn(currenciesStub);
    PowerMockito.when(pollingMarketDataServiceMock.getBleutradeMarkets()).thenReturn(marketsStub);

    Whitebox.setInternalState(exchange, "pollingMarketDataService", pollingMarketDataServiceMock);

    // when
    exchange.remoteInit();

    // then
    Map<Currency, CurrencyMetaData> currencyMetaDataMap = exchange.getMetaData().getCurrencyMetaDataMap();
    assertThat(currencyMetaDataMap).hasSize(2);
    assertThat(currencyMetaDataMap.get(Currency.BTC).scale).isEqualTo(8);
    assertThat(currencyMetaDataMap.get(Currency.LTC).scale).isEqualTo(8);

    Map<CurrencyPair, MarketMetaData> marketMetaDataMap = exchange.getMetaData().getMarketMetaDataMap();
    assertThat(marketMetaDataMap).hasSize(2);
    assertThat(marketMetaDataMap.get(CurrencyPair.DOGE_BTC).toString())
        .isEqualTo("MarketMetaData{tradingFee=0.0025, minimumAmount=0.10000000, priceScale=8}");
    assertThat(marketMetaDataMap.get(BLEU_BTC_CP).toString()).isEqualTo("MarketMetaData{tradingFee=0.0025, minimumAmount=1E-8, priceScale=8}");
  }
}
