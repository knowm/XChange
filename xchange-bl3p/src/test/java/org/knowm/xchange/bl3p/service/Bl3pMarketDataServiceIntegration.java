package org.knowm.xchange.bl3p.service;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bl3p.Bl3pExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class Bl3pMarketDataServiceIntegration {

  Exchange exchange = ExchangeFactory.INSTANCE.createExchange(Bl3pExchange.class);
  MarketDataService marketDataService = exchange.getMarketDataService();

  @Test
  public void getTicker() throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);

    System.out.println(ticker.toString());
  }

  @Test
  public void getOrderBook() throws IOException {
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);

    System.out.println(orderBook);
  }

  @Test
  public void getTrades() throws IOException {
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);

    System.out.println(trades);
  }
}
