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
package com.xeiam.xchange.examples.mtgox.v1.service.marketdata.polling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Mt Gox BTC exchange</li>
 * <li>Retrieving the last tick</li>
 * <li>Retrieving the current order book</li>
 * <li>Retrieving the current full order book</li>
 * <li>Retrieving trades</li>
 * </ul>
 */
public class MtGoxMarketdataDemo {

  private final Logger log = LoggerFactory.getLogger(MtGoxMarketdataDemo.class);

  public static void main(String[] args) {

    // Demonstrate the public market data service
    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = mtGox.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
    String btcusd = ticker.getLast().toString();
    System.out.println("Current exchange rate for BTC / USD: " + btcusd);

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getPartialOrderBook(Currencies.BTC, Currencies.USD);
    System.out.println("Current Order Book size for BTC / USD: " + orderBook.getAsks().size() + orderBook.getBids().size());

    // Get the current full orderbook
    OrderBook fullOrderBook = marketDataService.getFullOrderBook(Currencies.BTC, Currencies.USD);
    System.out.println("Current Full Order Book size for BTC / USD: " + fullOrderBook.getAsks().size() + fullOrderBook.getBids().size());

    // Get trades
    Trades trades = marketDataService.getTrades(Currencies.BTC, Currencies.PLN);
    System.out.println("Current trades size for BTC / PLN: " + trades.getTrades().size());

  }

}
