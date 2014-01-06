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
package com.xeiam.xchange.examples.campbx.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.campbx.service.polling.CampBXPollingMarketDataService;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Market Data from CampBX
 */
public class CampBXMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get CampBX exchange API using default settings
    Exchange campBXExchange = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());
    generic(campBXExchange);
    raw(campBXExchange);
  }

  private static void generic(Exchange campBXExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService campBXGenericMarketDataService = campBXExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = campBXGenericMarketDataService.getTicker(Currencies.BTC, Currencies.USD);

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = campBXGenericMarketDataService.getOrderBook(Currencies.BTC, Currencies.USD);

    System.out.println("Order book: " + orderBook);
  }

  private static void raw(Exchange campBXExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    CampBXPollingMarketDataService campBXspecificMarketDataService = (CampBXPollingMarketDataService) campBXExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    CampBXTicker tcampBXTicker = campBXspecificMarketDataService.getCampBXTicker(Currencies.BTC, Currencies.USD);

    System.out.println("Last: " + tcampBXTicker.getLast());
    System.out.println("Bid: " + tcampBXTicker.getBid());
    System.out.println("Ask: " + tcampBXTicker.getAsk());

    // Get the latest order book data for BTC/USD
    CampBXOrderBook campBXOrderBook = campBXspecificMarketDataService.getCampBXOrderBook(Currencies.BTC, Currencies.USD);

    System.out.println("Order book: " + campBXOrderBook);
  }

}
