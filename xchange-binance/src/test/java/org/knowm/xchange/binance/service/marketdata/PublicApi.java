package org.knowm.xchange.binance.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class PublicApi {

  @Test
  public void getTickerTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("LTC", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void getOrderBookTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("LTC", "BTC"));
    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }


}
