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
package com.xeiam.xchange.examples.bitcoinium;

import java.util.Date;

import com.xeiam.xchange.AuthHelper;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataService;
import com.xeiam.xchange.currency.Currencies;

/**
 * Demonstrates plotting an OrderBook with XChart
 * 
 * @author timmolter
 */
public class BitcoiniumTickerHistoryDemo {

  public static void main(String[] args) throws Exception {

    AuthHelper.trustAllCerts();

    // Use the factory to get Bitcoinium exchange API using default settings
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(BitcoiniumExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    BitcoiniumMarketDataService bitcoiniumMarketDataService = (BitcoiniumMarketDataService) bitcoiniumExchange.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the latest order book data for BTC/USD - MTGOX
    BitcoiniumTickerHistory bitcoiniumTickerHistory = bitcoiniumMarketDataService.getBitcoiniumTickerHistory(Currencies.BTC, Currencies.USD, "MTGOX", "2M");

    System.out.println(bitcoiniumTickerHistory.toString());

    long runninTimestamp = bitcoiniumTickerHistory.getBaseTimestamp();
    for (int i = 0; i < bitcoiniumTickerHistory.getPriceHistoryList().size(); i++) {

      Date timestamnp = new Date((runninTimestamp += bitcoiniumTickerHistory.getTimeStampOffsets().get(i)) * 1000);
      System.out.println(timestamnp + ": " + bitcoiniumTickerHistory.getPriceHistoryList().get(i));

    }

  }
}
