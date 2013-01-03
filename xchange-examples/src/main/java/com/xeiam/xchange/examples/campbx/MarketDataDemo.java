/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.campbx;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at Campbx
 */
public class MarketDataDemo {

  public static void main(String[] args) {

    // Use the factory to get Campbx exchange API using default settings
    Exchange campbx = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = campbx.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
    double value = ticker.getLast().getAmount().doubleValue();
    String currency = ticker.getLast().getCurrencyUnit().toString();

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());
    System.out.println("Volume: " + ticker.getVolume());
    System.out.println("High: " + ticker.getHigh());
    System.out.println("Low: " + ticker.getLow());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = marketDataService.getFullOrderBook(Currencies.BTC, Currencies.USD);

    System.out.println("Order book: " + orderBook);
  }

}
