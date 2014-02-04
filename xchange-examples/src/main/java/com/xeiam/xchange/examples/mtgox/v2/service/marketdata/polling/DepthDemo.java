/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.mtgox.v2.service.marketdata.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.MtGoxExchange;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthWrapper;
import com.xeiam.xchange.mtgox.v2.service.polling.MtGoxMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Test requesting depth at MtGox
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 2 MtGox exchange API using default settings
    Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = mtGoxExchange.getPollingMarketDataService();
    generic(marketDataService);
    raw((MtGoxMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(Currencies.BTC, Currencies.SEK);

    System.out.println("Current Order Book size for BTC / SEK: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(MtGoxMarketDataServiceRaw marketDataService) throws IOException {

    // Get the current orderbook
    MtGoxDepthWrapper orderBook = marketDataService.getMtGoxOrderBook(Currencies.BTC, Currencies.SEK, new Object[]{});
    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(orderBook.getMtGoxDepth().getAsks(), Currencies.SEK, "ask", "");
    List<LimitOrder> bids = MtGoxAdapters.adaptOrders(orderBook.getMtGoxDepth().getBids(), Currencies.SEK, "bid", "");

    System.out.println("Current Order Book size for BTC / SEK: " + (asks.size() + bids.size()));

    System.out.println("First Ask: " + asks.get(0).toString());

    System.out.println("First Bid: " + asks.get(0).toString());

    System.out.println(orderBook.toString());
  }

}
