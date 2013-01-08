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
package com.xeiam.xchange.examples.openexchangerates.marketdata;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Open Exchange Rates
 */
public class TickerDemo {

  public static void main(String[] args) {

    // Use the factory to get the Open Exchange Rates exchange API
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification("com.xeiam.xchange.oer.OERExchange");
    exchangeSpecification.setUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("ab32c922bca749ec9345b4717914ee1f");
    Exchange openExchangeRates = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the polling market data feed
    PollingMarketDataService marketDataService = openExchangeRates.getPollingMarketDataService();

    // Get the latest ticker data showing EUR/USD
    Ticker ticker = marketDataService.getTicker(Currencies.EUR, Currencies.USD);

    double value = ticker.getLast().getAmount().doubleValue();
    String currency = ticker.getLast().getCurrencyUnit().toString();
    System.out.println("Last: " + currency + "-" + value);

    System.out.println("Last: " + ticker.getLast().toString());

    ticker = marketDataService.getTicker(Currencies.JPY, Currencies.USD);
    System.out.println("Last: " + ticker.getLast().toString());

  }

}
