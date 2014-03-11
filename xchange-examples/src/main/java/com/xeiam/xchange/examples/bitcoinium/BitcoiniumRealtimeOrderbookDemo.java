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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.utils.CertHelper;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.XChartPanel;

/**
 * Demonstrates plotting an OrderBook with XChart
 * 
 * @author timmolter
 */
public class BitcoiniumRealtimeOrderbookDemo {

  BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService;
  public static final String BIDS_SERIES_NAME = "bids";
  List<BigDecimal> xAxisBidData;
  List<BigDecimal> yAxisBidData;
  public static final String ASKS_SERIES_NAME = "asks";
  List<BigDecimal> xAxisAskData;
  List<BigDecimal> yAxisAskData;

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();
    final BitcoiniumRealtimeOrderbookDemo bitcoiniumRealtimeTickerDemo = new BitcoiniumRealtimeOrderbookDemo();
    bitcoiniumRealtimeTickerDemo.go();
  }

  private void go() throws IOException {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("6seon0iepta86txluchde");
    // Use the factory to get the Open Exchange Rates exchange API
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public polling market data feed (no authentication)
    bitcoiniumMarketDataService = (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getPollingMarketDataService().getRaw();

    // Setup the panel
    final XChartPanel chartPanel = buildPanel();
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        // Create and set up the window.
        JFrame frame = new JFrame("XChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
      }
    });

    // Simulate a data feed
    TimerTask chartUpdaterTask = new TimerTask() {

      @Override
      public void run() {

        try {
          updateData();
          // update chart
          chartPanel.updateSeries(BIDS_SERIES_NAME, xAxisBidData, yAxisBidData);
          chartPanel.updateSeries(ASKS_SERIES_NAME, xAxisAskData, yAxisAskData);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(chartUpdaterTask, 0, 10000); // every ten seconds

  }

  public XChartPanel buildPanel() throws IOException {

    System.out.println("fetching data...");

    updateData();

    // create chart
    Chart chart = new ChartBuilder().chartType(ChartType.Area).width(800).height(400).title("Real-time Bitcoinium Order Book - BITSTAMP_BTC_USD").xAxisTitle("BTC").yAxisTitle("USD").build();
    chart.getStyleManager().setLegendPosition(LegendPosition.InsideNE);

    // add series
    Series series = chart.addSeries(BIDS_SERIES_NAME, xAxisBidData, yAxisBidData);
    series.setMarker(SeriesMarker.NONE);
    series = chart.addSeries(ASKS_SERIES_NAME, xAxisAskData, yAxisAskData);
    series.setMarker(SeriesMarker.NONE);

    return new XChartPanel(chart);
  }

  private void updateData() throws IOException {

    // /////////////////////////////////
    // Get the latest order book data for BTC/USD - MTGOX
    BitcoiniumOrderbook bitcoiniumOrderbook = bitcoiniumMarketDataService.getBitcoiniumOrderbook(Currencies.BTC, "BITSTAMP_USD", "10p");

    System.out.println(bitcoiniumOrderbook.toString());

    // build ticker history chart series data
    // Bids Series
    Collections.reverse(bitcoiniumOrderbook.getBidPriceList());
    Collections.reverse(bitcoiniumOrderbook.getBidVolumeList());
    xAxisBidData = bitcoiniumOrderbook.getBidPriceList();
    yAxisBidData = bitcoiniumOrderbook.getBidVolumeList();

    // Asks Series
    xAxisAskData = bitcoiniumOrderbook.getAskPriceList();
    yAxisAskData = bitcoiniumOrderbook.getAskVolumeList();

    // /////////////////////////////////
  }
}
