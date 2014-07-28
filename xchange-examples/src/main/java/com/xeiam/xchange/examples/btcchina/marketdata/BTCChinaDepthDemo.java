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
package com.xeiam.xchange.examples.btcchina.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author ObsessiveOrange
 *         Demonstrate requesting Order Book at BTC China
 */
public class BTCChinaDepthDemo {

  // Use the factory to get the BTCChina exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public polling market data feed (no authentication)
  static PollingMarketDataService marketDataService = btcchina.getPollingMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    // Get the latest order book data for BTC/CNY
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_LTC);

    // System.out.println(orderBook.toString());
    System.out.println("lowestAsk: " + orderBook.getAsks().get(0));
    System.out.println("asks Size: " + orderBook.getAsks().size());
    System.out.println("highestBid: " + orderBook.getBids().get(0));
    System.out.println("bids Size: " + orderBook.getBids().size());

  }

  public static void raw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    // Get the latest order book data for BTC/CNY
    BTCChinaDepth orderBook = ((BTCChinaMarketDataServiceRaw) marketDataService).getBTCChinaOrderBook("ltcbtc");

    System.out.println(orderBook.toString());

    // first item in each BigDecial[] will be price (in RMB), and second will be volume/depth.
    System.out.println("Asks:");
    for (BigDecimal[] currRow : orderBook.getAsks()) {
      for (BigDecimal currItem : currRow) {
        System.out.print(currItem.toPlainString() + ", ");
      }
      System.out.println();
    }
  }
}
