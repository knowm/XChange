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

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.utils.CertHelper;
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

    CertHelper.trustAllCerts();

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("6seon0iepta86txluchde");
    // Use the factory to get the Open Exchange Rates exchange API
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public polling market data feed (no authentication)
    BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService = (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the latest order book data for BTC/USD - BITSTAMP
    BitcoiniumOrderbook bitcoiniumOrderbook = bitcoiniumMarketDataService.getBitcoiniumOrderbook(Currencies.BTC, Currencies.USD, "BITSTAMP", "10p");

    System.out.println("Order book: " + bitcoiniumOrderbook);
    System.out.println("received data.");

    System.out.println("plotting...");

    // Create Chart
    Chart chart = new Chart(800, 500);

    // Customize Chart
    chart.setChartTitle("Bitcoinium Order Book - BITSTAMP_BTC_USD");
    chart.setYAxisTitle("BTC");
    chart.setXAxisTitle("USD");
    chart.getStyleManager().setChartType(ChartType.Area);

    // BIDS

    Collections.reverse(bitcoiniumOrderbook.getBidPriceList());
    Collections.reverse(bitcoiniumOrderbook.getBidVolumeList());

    // Bids Series
    Series series = chart.addSeries("bids", bitcoiniumOrderbook.getBidPriceList(), bitcoiniumOrderbook.getBidVolumeList());
    series.setMarker(SeriesMarker.NONE);

    // ASKS

    // Asks Series
    series = chart.addSeries("asks", bitcoiniumOrderbook.getAskPriceList(), bitcoiniumOrderbook.getAskVolumeList());
    series.setMarker(SeriesMarker.NONE);

    new SwingWrapper(chart).displayChart();

  }
}
