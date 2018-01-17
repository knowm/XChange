package org.knowm.xchange.kucoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KucoinIntegrationTests {

  private Exchange KUCOIN = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName());
  private CurrencyPair BTC_ETH = new CurrencyPair("BTC", "ETH");

  @Test
  public void tickerFetchTest() throws Exception {

    MarketDataService marketDataService = KUCOIN.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(BTC_ETH);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void ordersFetchTest() throws Exception {
    
    OrderBook orderBookDefault = KUCOIN.getMarketDataService().getOrderBook(BTC_ETH);
    assertThat(orderBookDefault).isNotNull();
    assertThat(orderBookDefault.getAsks().size()).isEqualTo(6);
    assertThat(orderBookDefault.getBids().size()).isEqualTo(6);

    OrderBook orderBookShort = KUCOIN.getMarketDataService().getOrderBook(BTC_ETH, 1);
    assertThat(orderBookShort).isNotNull();
    assertThat(orderBookShort.getAsks().size()).isEqualTo(1);
    assertThat(orderBookShort.getBids().size()).isEqualTo(1);

    OrderBook orderBookLong = KUCOIN.getMarketDataService().getOrderBook(BTC_ETH, 10);
    assertThat(orderBookLong).isNotNull();
    assertThat(orderBookLong.getAsks().size()).isEqualTo(10);
    assertThat(orderBookLong.getBids().size()).isEqualTo(10);
  }
}
