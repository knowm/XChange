package org.knowm.xchange.kucoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class MarketDataServiceIntegration {

  private static final Exchange KUCOIN =
      ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName());
  private static final CurrencyPair XRB_BTC = new CurrencyPair("XRB", "BTC");

  @Test
  public void testGetTicker() throws Exception {

    MarketDataService marketDataService = KUCOIN.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(XRB_BTC);
    assertThat(ticker.getAsk()).isGreaterThan(ticker.getBid());
    assertThat(ticker.getHigh()).isGreaterThan(ticker.getLow());
    assertThat(ticker.getCurrencyPair()).isEqualTo(XRB_BTC);
  }

  @Test
  public void testGetOrderBook() throws Exception {

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
  public void testGetTrades() throws Exception {

    Trades tradesDefault = KUCOIN.getMarketDataService().getTrades(XRB_BTC);
    assertThat(tradesDefault.getTrades().size()).isEqualTo(10);
    tradesDefault.getTrades().forEach(t -> assertThat(t.getCurrencyPair()).isEqualTo(XRB_BTC));

    Trades tradesLimit20 = KUCOIN.getMarketDataService().getTrades(XRB_BTC, 20);
    assertThat(tradesLimit20.getTrades().size()).isEqualTo(20);
    tradesLimit20.getTrades().forEach(t -> assertThat(t.getCurrencyPair()).isEqualTo(XRB_BTC));
  }
}
