package org.knowm.xchange.cobinhood.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cobinhood.CobinhoodExchange;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CobinhoodMarketDataServiceIntegration {

  private static final Exchange COBINHOOD =
      ExchangeFactory.INSTANCE.createExchange(CobinhoodExchange.class.getName());

  @Test
  public void testGetTicker() throws Exception {

    MarketDataService marketDataService = COBINHOOD.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(BTC_USDT);
    assertThat(ticker.getAsk()).isGreaterThan(ticker.getBid());
    assertThat(ticker.getHigh()).isGreaterThan(ticker.getLow());
    assertThat(ticker.getCurrencyPair()).isEqualTo(BTC_USDT);
  }

  @Test
  public void testGetOrderBook() throws Exception {

    OrderBook orderBookDefault = COBINHOOD.getMarketDataService().getOrderBook(BTC_USDT);
    assertThat(orderBookDefault).isNotNull();
    assertThat(orderBookDefault.getAsks().size()).isEqualTo(50);
    assertThat(orderBookDefault.getBids().size()).isEqualTo(50);

    OrderBook orderBookShort = COBINHOOD.getMarketDataService().getOrderBook(BTC_USDT, 1);
    assertThat(orderBookShort).isNotNull();
    assertThat(orderBookShort.getAsks().size()).isEqualTo(1);
    assertThat(orderBookShort.getBids().size()).isEqualTo(1);

    OrderBook orderBookLong = COBINHOOD.getMarketDataService().getOrderBook(BTC_USDT, 10);
    assertThat(orderBookLong).isNotNull();
    assertThat(orderBookLong.getAsks().size()).isEqualTo(10);
    assertThat(orderBookLong.getBids().size()).isEqualTo(10);
  }

  @Test
  public void testGetTrades() throws Exception {

    Trades tradesDefault = COBINHOOD.getMarketDataService().getTrades(BTC_USDT);
    assertThat(tradesDefault.getTrades().size()).isEqualTo(50);
    tradesDefault.getTrades().forEach(t -> assertThat(t.getCurrencyPair()).isEqualTo(BTC_USDT));

    Trades tradesLimit20 = COBINHOOD.getMarketDataService().getTrades(BTC_USDT, 20);
    assertThat(tradesLimit20.getTrades().size()).isEqualTo(20);
    tradesLimit20.getTrades().forEach(t -> assertThat(t.getCurrencyPair()).isEqualTo(BTC_USDT));
  }
}
