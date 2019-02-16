package org.knowm.xchange.coinmarketcap.pro.v1.service.marketdata;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.pro.v1.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CoinMarketCapMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TickerFetchIntegration {
  private static Exchange exchange;

  @BeforeClass
  public static void initExchange() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class);

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void getTickerTest() throws Exception {
    CoinMarketCapMarketDataService marketDataService =
        (CoinMarketCapMarketDataService) exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(ticker.getVolume()).isGreaterThan(new BigDecimal("0"));
    assertThat(ticker.getLast()).isGreaterThan(new BigDecimal("0"));
  }

  @Test
  public void getTickersTest() throws Exception {
    CoinMarketCapMarketDataService marketDataService =
            (CoinMarketCapMarketDataService) exchange.getMarketDataService();
    List<Ticker> tickerList = marketDataService.getTickers(null);

    System.out.println(tickerList);

    assertThat(tickerList).isNotNull();
    assertThat(tickerList).isNotEmpty();
  }
}
