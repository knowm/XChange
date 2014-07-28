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
package com.xeiam.xchange.examples.bitcurex;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcurex.BitcurexExchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at Bitcurex
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Bitcurex exchange API using default settings
    Exchange bitcurexEur = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    requestData(bitcurexEur, CurrencyPair.BTC_EUR);

    Exchange bitcurexPln = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    bitcurexPln.applySpecification(((BitcurexExchange) bitcurexPln).getDefaultExchangePLNSpecification());
    requestData(bitcurexPln, CurrencyPair.BTC_PLN);
  }

  private static void requestData(Exchange bitcurex, CurrencyPair pair) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcurex.getPollingMarketDataService();

    generic(marketDataService, pair);
    raw((BitcurexMarketDataServiceRaw) marketDataService, pair.counterSymbol);
  }

  private static void generic(PollingMarketDataService marketDataService, CurrencyPair pair) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(pair);

    System.out.println("Current Order Book size for BTC / " + pair.counterSymbol + ": " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(BitcurexMarketDataServiceRaw marketDataService, String counterSymbol) throws IOException {

    // Get the latest order book data for BTC/CAD
    BitcurexDepth bitcurexDepth = marketDataService.getBitcurexOrderBook(counterSymbol);

    System.out.println("Current Order Book size for BTC / " + counterSymbol + ": " + (bitcurexDepth.getAsks().size() + bitcurexDepth.getBids().size()));

    System.out.println("First Ask: " + bitcurexDepth.getAsks().get(0)[0].toString());

    System.out.println("First Bid: " + bitcurexDepth.getBids().get(0)[0].toString());

    System.out.println(bitcurexDepth.toString());
  }

}
