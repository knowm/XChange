package org.knowm.xchange.coinmarketcap.pro.v1.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcExchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CmcMarketDataService;

class CurrencyMapFetchIntegration {
  private static Exchange exchange;
  private static CmcMarketDataService cmcMarketDataService;

  @BeforeAll
  static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CmcExchange.class);
    exchange.applySpecification(((CmcExchange) exchange).getSandboxExchangeSpecification());
    cmcMarketDataService = (CmcMarketDataService) exchange.getMarketDataService();

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);
  }

  @Test
  void getCmcCurrencyListTest() throws Exception {
    List<CmcCurrency> currencyList = cmcMarketDataService.getCmcCurrencyList();

    assertThat(currencyList).isNotNull();
    assertThat(currencyList.size()).isGreaterThan(0);

    Optional<CmcCurrency> btcOptional =
        currencyList.stream().filter(currency -> "BTC".equals(currency.getSymbol())).findAny();
    CmcCurrency btc = btcOptional.get();
    assertThat(btc).isNotNull();
  }
}
