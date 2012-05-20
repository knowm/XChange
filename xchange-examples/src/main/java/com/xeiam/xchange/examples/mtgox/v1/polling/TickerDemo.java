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
package com.xeiam.xchange.examples.mtgox.v1.polling;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * Test requesting polling Ticker at MtGox
 */
public class TickerDemo {

  private static PollingMarketDataService marketDataService;

  public static void main(String[] args) {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public polling market data feed (no authentication)
    marketDataService = mtGox.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
    double value = ticker.getLast().getAmount().doubleValue();
    String currency = ticker.getLast().getCurrencyUnit().toString();
    System.out.println("Last: " + currency + "-" + value);

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());

    // Get the latest ticker data showing BTC to EUR
    ticker = marketDataService.getTicker(Currencies.BTC, Currencies.EUR);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());

    // Get the latest ticker data showing BTC to GBP
    ticker = marketDataService.getTicker(Currencies.BTC, Currencies.GBP);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());

  }

}
