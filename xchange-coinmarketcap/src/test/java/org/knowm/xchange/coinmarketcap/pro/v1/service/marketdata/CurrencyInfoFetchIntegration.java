package org.knowm.xchange.coinmarketcap.pro.v1.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcExchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CmcMarketDataService;
import org.knowm.xchange.currency.Currency;

public class CurrencyInfoFetchIntegration {
  private static Exchange exchange;
  private static CmcMarketDataService cmcMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CmcExchange.class);
    exchange.applySpecification(((CmcExchange) exchange).getSandboxExchangeSpecification());
    cmcMarketDataService = (CmcMarketDataService) exchange.getMarketDataService();

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void getCmcCurrencyInfoTest() throws Exception {
    CmcCurrencyInfo currency = cmcMarketDataService.getCmcCurrencyInfo(Currency.BTC);

    assertThat(currency).isNotNull();
    assertThat(currency.getSymbol()).isEqualTo(Currency.BTC.getSymbol());
  }

  @Test
  public void getCmcMultipleCurrencyInfoTest() throws Exception {
    List<Currency> currencyList = Arrays.asList(Currency.BTC, Currency.ETH, Currency.LTC);

    Map<String, CmcCurrencyInfo> currencyInfoMap =
        cmcMarketDataService.getCmcMultipleCurrencyInfo(currencyList);

    assertThat(currencyInfoMap).isNotNull();
    assertThat(currencyInfoMap.size()).isEqualTo(3);
    assertThat(currencyInfoMap.get(Currency.BTC.getSymbol())).isNotNull();
    assertThat(currencyInfoMap.get(Currency.ETH.getSymbol())).isNotNull();
    assertThat(currencyInfoMap.get(Currency.LTC.getSymbol())).isNotNull();
  }
}
