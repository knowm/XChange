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

import java.util.Collections;

import com.xeiam.xchange.AuthHelper;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataService;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.SwingWrapper;

/**
 * Demonstrates plotting an OrderBook with XChart
 * 
 * @author timmolter
 */
public class BitcoiniumOrderBookChartDemo {

  public static void main(String[] args) throws Exception {

    AuthHelper.trustAllCerts();

    // Use the factory to get Bitcoinium exchange API using default settings
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(BitcoiniumExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    BitcoiniumMarketDataService bitcoiniumMarketDataService = (BitcoiniumMarketDataService) bitcoiniumExchange.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the latest order book data for BTC/USD - MTGOX
    BitcoiniumOrderbook bitcoiniumOrderBook = bitcoiniumMarketDataService.getBitcoiniumOrderbook(Currencies.BTC, Currencies.USD, "MTGOX", "10p");

    System.out.println("Order book: " + bitcoiniumOrderBook);
    System.out.println("received data.");

    System.out.println("plotting...");

    // Create Chart
    Chart chart = new Chart(800, 500);

    // Customize Chart
    chart.setChartTitle("Bitcoinium Order Book - MTGOX_BTC_USD");
    chart.setYAxisTitle("BTC");
    chart.setXAxisTitle("USD");
    chart.getStyleManager().setChartType(ChartType.Area);

    // BIDS

    Collections.reverse(bitcoiniumOrderBook.getBidPriceList());
    Collections.reverse(bitcoiniumOrderBook.getBidVolumeList());

    // Bids Series
    Series series = chart.addSeries("bids", bitcoiniumOrderBook.getBidPriceList(), bitcoiniumOrderBook.getBidVolumeList());
    series.setMarker(SeriesMarker.NONE);

    // ASKS

    // Asks Series
    series = chart.addSeries("asks", bitcoiniumOrderBook.getAskPriceList(), bitcoiniumOrderBook.getAskVolumeList());
    series.setMarker(SeriesMarker.NONE);

    new SwingWrapper(chart).displayChart();

  }
}
