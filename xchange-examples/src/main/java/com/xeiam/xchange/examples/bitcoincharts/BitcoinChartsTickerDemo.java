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
package com.xeiam.xchange.examples.bitcoincharts;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsExchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demo requesting polling Ticker at BitcoinCharts
 * 
 * @author timmolter
 */
public class BitcoinChartsTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinCharts exchange API using default settings
    Exchange bitcoinChartsExchange = ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcoinChartsExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC/bitstampUSD
    CurrencyPair currencyPair = new CurrencyPair(Currencies.BTC, "bitstampUSD");
    Ticker ticker = marketDataService.getTicker(currencyPair);

    double value = ticker.getLast().doubleValue();

    String currency = ticker.getCurrencyPair().counterCurrency.toString();
    System.out.println("bitstampUSD Last: " + currency + "-" + value);
    System.out.println("bitstampUSD Last: " + ticker.getLast().toString());

  }
}
