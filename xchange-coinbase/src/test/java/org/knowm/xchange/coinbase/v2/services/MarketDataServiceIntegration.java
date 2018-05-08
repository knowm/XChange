package org.knowm.xchange.coinbase.v2.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseCurrencyData.CoinbaseCurrency;
import org.knowm.xchange.coinbase.v2.service.CoinbaseMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class MarketDataServiceIntegration {

  static Exchange exchange;
  static MarketDataService marketDataService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    marketDataService = exchange.getMarketDataService();
  }

  @Test
  public void listCurrencies() throws Exception {

    CoinbaseMarketDataService coinbaseService = (CoinbaseMarketDataService) marketDataService;
    List<CoinbaseCurrency> currencies = coinbaseService.getCoinbaseCurrencies();
    assertThat(currencies).contains(new CoinbaseCurrency("Bitcoin", "BTC"));
  }

  @Test
  public void listExchageRates() throws Exception {

    CoinbaseMarketDataService coinbaseService = (CoinbaseMarketDataService) marketDataService;
    Map<String, BigDecimal> exchangeRates = coinbaseService.getCoinbaseExchangeRates();
    Assert.assertTrue(exchangeRates.get("EUR") instanceof BigDecimal);
  }

  @Test
  public void listPrices() throws Exception {

    CoinbaseMarketDataService coinbaseService = (CoinbaseMarketDataService) marketDataService;
    CoinbasePrice money = coinbaseService.getCoinbaseBuyPrice(Currency.BTC, Currency.USD);
    assertThat(money)
        .hasFieldOrPropertyWithValue("currency", Currency.USD)
        .hasNoNullFieldsOrProperties();

    money = coinbaseService.getCoinbaseSellPrice(Currency.BTC, Currency.USD);
    assertThat(money)
        .hasFieldOrPropertyWithValue("currency", Currency.USD)
        .hasNoNullFieldsOrProperties();

    money = coinbaseService.getCoinbaseSpotRate(Currency.BTC, Currency.USD);
    assertThat(money)
        .hasFieldOrPropertyWithValue("currency", Currency.USD)
        .hasNoNullFieldsOrProperties();

    money = coinbaseService.getCoinbaseSpotRate(Currency.BTC, Currency.USD);
    assertThat(money)
        .hasFieldOrPropertyWithValue("currency", Currency.USD)
        .hasNoNullFieldsOrProperties();

    money = coinbaseService.getCoinbaseHistoricalSpotRate(Currency.BTC, Currency.USD, new Date());
    assertThat(money)
        .hasFieldOrPropertyWithValue("currency", Currency.USD)
        .hasNoNullFieldsOrProperties();
  }
}
