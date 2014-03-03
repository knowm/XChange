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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
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
public class BitcoiniumRealtimeTickerDemo {

  BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService;
  List<Date> xAxisData;
  List<Double> yAxisData;
  public static final String SERIES_NAME = "MtGox USD/BTC";

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();
    final BitcoiniumRealtimeTickerDemo bitcoiniumRealtimeTickerDemo = new BitcoiniumRealtimeTickerDemo();
    bitcoiniumRealtimeTickerDemo.go();
  }

  private void go() throws IOException {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("6seon0iepta86txluchde");
    // Use the factory to get the Open Exchange Rates exchange API
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public polling market data feed (no authentication)
    bitcoiniumMarketDataService = (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getPollingMarketDataService();

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
          BitcoiniumTicker bitcoiniumTicker = bitcoiniumMarketDataService.getBitcoiniumTicker(Currencies.BTC, Currencies.USD, "BITSTAMP");
          System.out.println(bitcoiniumTicker.toString());
          Date timestamp = new Date(bitcoiniumTicker.getTimestamp());
          double price = bitcoiniumTicker.getLast().doubleValue();
          if (xAxisData.get(xAxisData.size() - 1).getTime() != timestamp.getTime()) {
            xAxisData.add(timestamp);
            yAxisData.add(price);
            Series series = chartPanel.updateSeries(SERIES_NAME, xAxisData, yAxisData);
            System.out.println(series.getXData());
            System.out.println(series.getYData());
          }
          else {
            System.out.println("No new data.");
          }
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

    // Get the latest order book data for BTC/USD - BITSTAMP
    BitcoiniumTickerHistory bitcoiniumTickerHistory = bitcoiniumMarketDataService.getBitcoiniumTickerHistory(Currencies.BTC, Currencies.USD, "BITSTAMP", "3h");

    System.out.println(bitcoiniumTickerHistory.toString());

    // build ticker history chart series data
    xAxisData = new ArrayList<Date>();
    yAxisData = new ArrayList<Double>();
    long runninTimestamp = bitcoiniumTickerHistory.getBaseTimestamp();
    for (int i = 0; i < bitcoiniumTickerHistory.getPriceHistoryList().size(); i++) {

      Date timestamp = new Date((runninTimestamp += bitcoiniumTickerHistory.getTimeStampOffsets().get(i)) * 1000);
      double price = bitcoiniumTickerHistory.getPriceHistoryList().get(i).doubleValue();
      System.out.println(timestamp + ": " + price);
      xAxisData.add(timestamp);
      yAxisData.add(price);
    }

    // create chart
    Chart chart = new ChartBuilder().chartType(ChartType.Area).width(800).height(400).title("Real-time MtGox Price vs. Time").xAxisTitle("Time").yAxisTitle("Price").build();
    chart.getStyleManager().setLegendPosition(LegendPosition.InsideNE);

    // add series
    Series series = chart.addSeries(SERIES_NAME, xAxisData, yAxisData);
    series.setMarker(SeriesMarker.NONE);

    return new XChartPanel(chart);
  }
}
