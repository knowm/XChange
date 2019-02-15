package org.knowm.xchange.coinmarketcap.pro.v1.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmarketcap.pro.v1.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CoinMarketCapMarketDataService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyMapFetchIntegration {
  private static Exchange exchange;

  @BeforeClass
  public static void initExchange() {
    ExchangeSpecification specification = new ExchangeSpecification(CoinMarketCapExchange.class);
    exchange = ExchangeFactory.INSTANCE.createExchange(specification);
  }

  @Test
  public void getCurrencyListTest() throws Exception {
    CoinMarketCapMarketDataService marketDataService =
        (CoinMarketCapMarketDataService) exchange.getMarketDataService();
    List<CoinMarketCapCurrency> currencyList = marketDataService.getCurrencyList();

    System.out.println(currencyList);
    assertThat(currencyList).isNotNull();
    assertThat(currencyList.size()).isGreaterThan(0);
  }
}
