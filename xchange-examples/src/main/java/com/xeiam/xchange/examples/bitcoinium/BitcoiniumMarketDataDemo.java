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

import java.io.IOException;

import com.xeiam.xchange.AuthHelper;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Market Data from CampBX
 */
public class BitcoiniumMarketDataDemo {

  public static void main(String[] args) throws Exception {

    AuthHelper.trustAllCerts();

    // Use the factory to get Bitcoinium exchange API using default settings
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(BitcoiniumExchange.class.getName());
    generic(bitcoiniumExchange);
    raw(bitcoiniumExchange);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService bitcoiniumGenericMarketDataService = exchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = bitcoiniumGenericMarketDataService.getTicker(Currencies.BTC, Currencies.USD, "MTGOX");

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());
    System.out.println("Volume: " + ticker.getVolume());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = bitcoiniumGenericMarketDataService.getOrderBook(Currencies.BTC, Currencies.USD, "MTGOX", "10p");

    System.out.println("Order book: " + orderBook);
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    BitcoiniumMarketDataServiceRaw bitcoiniumSpecificMarketDataService = (BitcoiniumMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    BitcoiniumTicker bitcoiniumTicker = bitcoiniumSpecificMarketDataService.getBitcoiniumTicker(Currencies.BTC, Currencies.USD, "MTGOX");

    System.out.println("Last: " + bitcoiniumTicker.getLast());
    System.out.println("Bid: " + bitcoiniumTicker.getBid());
    System.out.println("Ask: " + bitcoiniumTicker.getAsk());
    System.out.println("Volume: " + bitcoiniumTicker.getVolume());

    // Get the latest order book data for BTC/USD - MtGox
    BitcoiniumOrderbook bitcoiniumOrderbook = bitcoiniumSpecificMarketDataService.getBitcoiniumOrderbook(Currencies.BTC, Currencies.USD, "MTGOX", "10p");

    System.out.println("Order book: " + bitcoiniumOrderbook);
  }
}
