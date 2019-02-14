package org.knowm.xchange.coinmarketcap.pro.v1.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmarketcap.pro.v1.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CoinMarketCapMarketDataService;
import org.knowm.xchange.currency.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyInfoFetchIntegration {
  private static Exchange exchange;

  @BeforeClass
  public static void initExchange() {
    ExchangeSpecification specification = new ExchangeSpecification(CoinMarketCapExchange.class);
    exchange = ExchangeFactory.INSTANCE.createExchange(specification);
  }

  @Test
  public void getCurrencyInfoTest() throws Exception {
    CoinMarketCapMarketDataService marketDataService =
        (CoinMarketCapMarketDataService) exchange.getMarketDataService();
    CoinMarketCapCurrencyInfo currency = marketDataService.getCurrencyInfo(Currency.BTC);

    System.out.println(currency);

    assertThat(currency).isNotNull();
    assertThat(currency.getSymbol()).isEqualTo(Currency.BTC.getSymbol());

  }
}
