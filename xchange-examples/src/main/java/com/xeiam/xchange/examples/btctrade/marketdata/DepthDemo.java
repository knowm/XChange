/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.btctrade.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting order book at BTCTrade.
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTrade exchange API using default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication).
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the order book data for BTC/CNY.
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook);
    System.out.println("asks: " + orderBook.getAsks());
    System.out.println("bids: " + orderBook.getBids());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange exchange) {

    // Interested in the public polling market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService = (BTCTradeMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the order book data for BTC/CNY.
    BTCTradeDepth depth = marketDataService.getBTCTradeDepth();
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));
  }

}
