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
package com.xeiam.xchange.examples.justcoin.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.justcoin.JustcoinExchange;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.justcoin.service.polling.JustcoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class JustcoinMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Justcoin exchange API using default settings
    Exchange justcoinExchange = ExchangeFactory.INSTANCE.createExchange(JustcoinExchange.class.getName());
    generic(justcoinExchange);
    raw(justcoinExchange);
  }

  private static void generic(Exchange justcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService justcoinGenericMarketDataService = justcoinExchange.getPollingMarketDataService();

    // Get the latest ticker data for the BTC/LTC market
    Ticker ticker = justcoinGenericMarketDataService.getTicker(CurrencyPair.BTC_LTC);
    System.out.println(ticker);

    // Get the latest order book data for BTC/LTC
    OrderBook orderBook = justcoinGenericMarketDataService.getOrderBook(CurrencyPair.BTC_LTC);
    System.out.println("Order book: " + orderBook);
  }

  private static void raw(Exchange justcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    JustcoinMarketDataServiceRaw justcoinSpecificMarketDataService = (JustcoinMarketDataServiceRaw) justcoinExchange.getPollingMarketDataService();

    Collection<CurrencyPair> currencyPairs = justcoinSpecificMarketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    // Get the latest ticker data for all markets on the Justcoin Exchange
    JustcoinTicker[] justcoinTickers = justcoinSpecificMarketDataService.getTickers();

    System.out.println(Arrays.toString(justcoinTickers));

    // Get the latest market depth data for BTC/LTC
    JustcoinDepth justcoinMarketDepth = justcoinSpecificMarketDataService.getMarketDepth(Currencies.BTC, Currencies.LTC);

    System.out.println(justcoinMarketDepth);
  }

}
