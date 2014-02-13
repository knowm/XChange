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
package com.xeiam.xchange.examples.btce.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.service.polling.BTCEMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at BTC-E
 */
public class DepthDemo {

  // Use the factory to get BTC-E exchange API using default settings
    static Exchange btce = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());
  // Interested in the public polling market data feed (no authentication)
    static PollingMarketDataService marketDataService = btce.getPollingMarketDataService();

  public static void main(String[] args) throws IOException {
    generic();
    raw();
  }  

  public static void generic() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
    // Get the latest full order book data for LTC/USD
    OrderBook orderBook = marketDataService.getOrderBook(Currencies.LTC, Currencies.USD);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial order book (2000 entries) data for BTC/USD
    orderBook = marketDataService.getOrderBook(Currencies.BTC, Currencies.USD, 2000);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial size order book (3 entries) data for BTC/USD
    orderBook = marketDataService.getOrderBook(Currencies.BTC, Currencies.USD, 3);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

  }
  
  public static void raw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
	// Get the latest full order book data for LTC/USD
	BTCEDepthWrapper orderBook = ((BTCEMarketDataServiceRaw)marketDataService).getBTCEOrderBook(Currencies.LTC, Currencies.USD);
	System.out.println(orderBook.toString());

	// Get the latest partial order book (2000 entries) data for BTC/USD
	orderBook = ((BTCEMarketDataServiceRaw)marketDataService).getBTCEOrderBook(Currencies.BTC, Currencies.USD, 2000);
	System.out.println(orderBook.toString());

	// Get the latest partial size order book (3 entries) data for BTC/USD
	orderBook = ((BTCEMarketDataServiceRaw)marketDataService).getBTCEOrderBook(Currencies.BTC, Currencies.USD, 3);
	System.out.println(orderBook.toString());

	  }

}
