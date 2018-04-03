package org.knowm.xchange.bleutrade;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.service.BleutradeServiceTestSupport;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(MockitoJUnitRunner.class)
public class BleutradeExchangeIntegration extends BleutradeServiceTestSupport {
  private BleutradeExchange exchange;

  @Before
  public void setUp() {
    exchange =
        spy(
            (BleutradeExchange)
                ExchangeFactory.INSTANCE.createExchange(
                    BleutradeExchange.class.getCanonicalName()));
  }

  @Test
  public void shouldRemoteInit() throws IOException {
    // when
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    // then
    verify(exchange).remoteInit();
  }

  @Test(expected = NullPointerException.class)
  public void shouldFailWhenApplyNullSpecification() {
    // when
    exchange.applySpecification(null);

    // then
    fail(
        "BTCMarketsExchange should throw NullPointerException when tries to apply null specification");
  }

  @Test
  public void shouldCreateDefaultExchangeSpecification() {
    // when
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();

    // then
    assertThat(specification.getExchangeClassName())
        .is(
            new Condition<>(
                (String s) -> {
                  try {
                    return BleutradeExchange.class.isAssignableFrom(Class.forName(s));
                  } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                  }
                },
                "Is assignable from BleutradeExchange"));
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
    BleutradeCurrenciesReturn bleutradeCurrenciesReturn =
        new BleutradeCurrenciesReturn(
            asList(
                createBleutradeCurrency(
                    "BTC", "Bitcoin", 2, new BigDecimal("0.00080000"), true, "BITCOIN"),
                createBleutradeCurrency(
                    "LTC", "Litecoin", 4, new BigDecimal("0.02000000"), true, "BITCOIN")));

    BleutradeMarketsReturn bleutradeMarketsReturn =
        new BleutradeMarketsReturn(
            asList(
                createBleutradeMarket(
                    "DOGE",
                    "BTC",
                    "Dogecoin",
                    "Bitcoin",
                    new BigDecimal("0.10000000"),
                    "DOGE_BTC",
                    true),
                createBleutradeMarket(
                    "BLEU",
                    "BTC",
                    "Bleutrade Share",
                    "Bitcoin",
                    new BigDecimal("0.00000001"),
                    "BLEU_BTC",
                    true)));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    when(bleutrade.getBleutradeCurrencies()).thenReturn(bleutradeCurrenciesReturn);
    when(bleutrade.getBleutradeMarkets()).thenReturn(bleutradeMarketsReturn);

    IRestProxyFactory restProxyFactory = mock(IRestProxyFactory.class);
    when(restProxyFactory.createProxy(
            eq(BleutradeAuthenticated.class), any(String.class), any(ClientConfig.class)))
        .thenReturn(bleutrade);

    BleutradeExchange mockExchange = new BleutradeExchange(restProxyFactory);
    mockExchange.applySpecification(mockExchange.getDefaultExchangeSpecification());
    mockExchange.initServices();

    // when
    mockExchange.remoteInit();

    // then
    Map<Currency, CurrencyMetaData> currencyMetaDataMap =
        mockExchange.getExchangeMetaData().getCurrencies();
    assertThat(currencyMetaDataMap).hasSize(2);
    assertThat(currencyMetaDataMap.get(Currency.BTC).getScale()).isEqualTo(8);
    assertThat(currencyMetaDataMap.get(Currency.LTC).getScale()).isEqualTo(8);

    Map<CurrencyPair, CurrencyPairMetaData> marketMetaDataMap =
        mockExchange.getExchangeMetaData().getCurrencyPairs();
    assertThat(marketMetaDataMap).hasSize(2);
    assertThat(marketMetaDataMap.get(CurrencyPair.DOGE_BTC).toString())
        .isEqualTo(
            "CurrencyPairMetaData [tradingFee=0.0025, minimumAmount=0.10000000, maximumAmount=null, priceScale=8]");
    assertThat(marketMetaDataMap.get(BLEU_BTC_CP).toString())
        .isEqualTo(
            "CurrencyPairMetaData [tradingFee=0.0025, minimumAmount=1E-8, maximumAmount=null, priceScale=8]");
  }
}
