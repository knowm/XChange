/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmate.CoinmateExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Martin Stachon */
public class CoinmateBaseServiceIntegration {

  @Test
  public void tickerFetchTestBTC_EUR() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "EUR"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void tickerFetchTestBTC_CZK() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CZK"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void tickerFetchTestLTC_BTC() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.LTC_BTC);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void orderBookFetchTestBTC_EUR() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);
    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }

  @Test
  public void orderBookFetchTestBTC_CZK() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CZK);
    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }

  @Test
  public void orderBookFetchTestLTC_BTC() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.LTC_BTC);
    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }

  @Test
  public void tradesFetchTestBTC_EUR() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    System.out.println(trades.getTrades().toString());
    assertThat(trades).isNotNull();
  }

  @Test
  public void tradesFetchTestBTC_CZK() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CZK);
    System.out.println(trades.getTrades().toString());
    assertThat(trades).isNotNull();
  }

  @Test
  public void tradesFetchTestLTC_BTC() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.LTC_BTC);
    System.out.println(trades.getTrades().toString());
    assertThat(trades).isNotNull();
  }
}
