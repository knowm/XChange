package org.knowm.xchange.coincheck.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coincheck.CoincheckExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class OrderBookFetchIntegration {

  @Test
  public void orderBookFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoincheckExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook book = marketDataService.getOrderBook(CurrencyPair.BTC_JPY);
    System.out.println(book.toString());
    assertThat(book).isNotNull();
  }
}
