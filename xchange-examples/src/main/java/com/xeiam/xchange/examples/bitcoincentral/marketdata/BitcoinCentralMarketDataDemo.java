/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.bitcoincentral.marketdata;

import java.util.List;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoincentral.BitcoinCentralExchange;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitcoin Central exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li> Please provide your username and password as program arguments.
 * </ul>
 */
public class BitcoinCentralMarketDataDemo {

  public static void main(String[] args) {

    ExchangeSpecification exSpec = new ExchangeSpecification(BitcoinCentralExchange.class);
    exSpec.setUri("https://en.bitcoin-central.net");

    Exchange btcCentral = ExchangeFactory.INSTANCE.createExchange(exSpec);

    PollingMarketDataService service = btcCentral.getPollingMarketDataService();

    List<CurrencyPair> symbols = service.getExchangeSymbols();
    System.out.println("symbols = " + symbols);

    OrderBook fullOrderBook = service.getFullOrderBook("BTC", "EUR");
    System.out.println("fullOrderBook = " + fullOrderBook);

    OrderBook partialOrderBook = service.getPartialOrderBook("BTC", "EUR");
    System.out.println("partialOrderBook = " + partialOrderBook);

    Ticker ticker = service.getTicker("BTC", "EUR");
    System.out.println("ticker = " + ticker);

    Trades trades = service.getTrades("BTC", "EUR");
    System.out.println("trades = " + trades);

  }
}
