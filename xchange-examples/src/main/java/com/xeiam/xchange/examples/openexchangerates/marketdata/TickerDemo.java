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
package com.xeiam.xchange.examples.openexchangerates.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.oer.OERExchange;
import com.xeiam.xchange.oer.dto.marketdata.OERRates;
import com.xeiam.xchange.oer.service.polling.OERMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Open Exchange Rates
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Open Exchange Rates exchange API
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(OERExchange.class.getName());
    exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("ab32c922bca749ec9345b4717914ee1f");

    Exchange openExchangeRates = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    generic(openExchangeRates);
    raw(openExchangeRates);

  }

  private static void generic(Exchange openExchangeRates) throws IOException {

    // Interested in the polling market data feed
    PollingMarketDataService marketDataService = openExchangeRates.getPollingMarketDataService();

    // Get the latest ticker data showing EUR/USD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.EUR_USD);
    System.out.println("Last: " + ticker.getLast().toString());

    // Alternate way to print out ticker currency and amount
    double value = ticker.getLast().doubleValue();
    System.out.println("ticker: " + ticker.toString());

    // Request another ticker. it will return a cached object
    ticker = marketDataService.getTicker(CurrencyPair.JPY_USD);
    System.out.println("cached Last: " + ticker.getLast().toString());

    // Request BTC ticker. it will return a cached object
    ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println("cached Last: " + ticker.getLast().toString());
  }

  private static void raw(Exchange openExchangeRates) throws IOException {

    OERMarketDataServiceRaw oERMarketDataServiceRaw = (OERMarketDataServiceRaw) openExchangeRates.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    OERRates oERRates = oERMarketDataServiceRaw.getOERTicker(CurrencyPair.BTC_USD);

    System.out.println(oERRates.toString());
  }
}
