package org.knowm.xchange.kucoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KucoinIntegrationTests {

  private Exchange KUCOIN = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName());
  private CurrencyPair XRB_BTC = new CurrencyPair("XRB", "BTC");

  @Test
  public void tickerFetchTest() throws Exception {

    MarketDataService marketDataService = KUCOIN.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(XRB_BTC);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void ordersFetchTest() throws Exception {
    
    OrderBook orderBookDefault = KUCOIN.getMarketDataService().getOrderBook(XRB_BTC);
    assertThat(orderBookDefault).isNotNull();
    assertThat(orderBookDefault.getAsks().size()).isEqualTo(6);
    assertThat(orderBookDefault.getBids().size()).isEqualTo(6);

    OrderBook orderBookShort = KUCOIN.getMarketDataService().getOrderBook(XRB_BTC, 1);
    assertThat(orderBookShort).isNotNull();
    assertThat(orderBookShort.getAsks().size()).isEqualTo(1);
    assertThat(orderBookShort.getBids().size()).isEqualTo(1);

    OrderBook orderBookLong = KUCOIN.getMarketDataService().getOrderBook(XRB_BTC, 10);
    assertThat(orderBookLong).isNotNull();
    assertThat(orderBookLong.getAsks().size()).isEqualTo(10);
    assertThat(orderBookLong.getBids().size()).isEqualTo(10);
  }

  @Test
  public void tradesFetchTest() throws Exception {

    Trades tradesDefault = KUCOIN.getMarketDataService().getTrades(XRB_BTC);
    System.out.println(tradesDefault.toString());
    assertThat(tradesDefault.getTrades().size()).isEqualTo(10);
    
    Trades tradesLimit20 = KUCOIN.getMarketDataService().getTrades(XRB_BTC, 20);
    System.out.println("LIMIT 20");
    System.out.println(tradesLimit20.toString());
    assertThat(tradesLimit20.getTrades().size()).isEqualTo(20);
    
    // couldnt get test case below to work, no idea how the since parameter works
//    Date since = tradesLimit20.getTrades().stream()
//        .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
//        .findFirst().get().getTimestamp();
//    Trades tradesSinceLastCall = KUCOIN.getMarketDataService().getTrades(BTC_ETH, 20, since);
//    System.out.println("WITH SINCE");
//    System.out.println(since);
//    System.out.println(tradesSinceLastCall.toString());
//    assertThat(tradesSinceLastCall.getTrades().size()).isLessThan(20);
  }
}
