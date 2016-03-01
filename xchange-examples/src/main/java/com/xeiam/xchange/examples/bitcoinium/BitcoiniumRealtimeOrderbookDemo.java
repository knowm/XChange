package com.xeiam.xchange.examples.bitcoinium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.knowm.xchart.ChartBuilder_XY;
import org.knowm.xchart.Chart_XY;
import org.knowm.xchart.Series_XY;
import org.knowm.xchart.Series_XY.ChartXYSeriesRenderStyle;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.style.Styler.LegendPosition;
import org.knowm.xchart.internal.style.markers.SeriesMarkers;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook.CondensedOrder;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataServiceRaw;

/**
 * Demonstrates plotting an OrderBook with XChart
 *
 * @author timmolter
 */
public class BitcoiniumRealtimeOrderbookDemo {

  BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService;
  public static final String BIDS_SERIES_NAME = "bids";
  List<Float> xAxisBidData;
  List<Float> yAxisBidData;
  public static final String ASKS_SERIES_NAME = "asks";
  List<Float> xAxisAskData;
  List<Float> yAxisAskData;

  public static void main(String[] args) throws Exception {

    final BitcoiniumRealtimeOrderbookDemo bitcoiniumRealtimeTickerDemo = new BitcoiniumRealtimeOrderbookDemo();
    bitcoiniumRealtimeTickerDemo.go();
  }

  private void go() throws IOException {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    // exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    System.out.println(exchangeSpecification.toString());
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public polling market data feed (no authentication)
    bitcoiniumMarketDataService = (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getPollingMarketDataService();

    // Setup the panel
    final XChartPanel<Chart_XY> chartPanel = buildPanel();
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
          chartPanel.updateSeries(BIDS_SERIES_NAME, xAxisBidData, yAxisBidData, null);
          chartPanel.updateSeries(ASKS_SERIES_NAME, xAxisAskData, yAxisAskData, null);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(chartUpdaterTask, 0, 10000); // every ten seconds

  }

  public XChartPanel<Chart_XY> buildPanel() throws IOException {

    System.out.println("fetching data...");

    updateData();

    // create chart
    Chart_XY chart = new ChartBuilder_XY().width(800).height(400).title("Real-time Bitcoinium Order Book - BITSTAMP_BTC_USD").xAxisTitle("BTC")
        .yAxisTitle("USD").build();

    // Customize Chart
    chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
    chart.getStyler().setDefaultSeriesRenderStyle(ChartXYSeriesRenderStyle.Area);

    // add series
    Series_XY series = chart.addSeries(BIDS_SERIES_NAME, xAxisBidData, yAxisBidData);
    series.setMarker(SeriesMarkers.NONE);
    series = chart.addSeries(ASKS_SERIES_NAME, xAxisAskData, yAxisAskData);
    series.setMarker(SeriesMarkers.NONE);

    return new XChartPanel<Chart_XY>(chart);
  }

  private void updateData() throws IOException {

    // /////////////////////////////////
    // Get the latest order book data for BTC/USD - MTGOX
    BitcoiniumOrderbook bitcoiniumOrderbook = bitcoiniumMarketDataService.getBitcoiniumOrderbook("BTC", "BITSTAMP_USD", "TEN_PERCENT");

    System.out.println(bitcoiniumOrderbook.toString());

    // build ticker history chart series data
    // Bids Series

    List<Float> bidsPriceData = getPriceData(bitcoiniumOrderbook.getBids());
    Collections.reverse(bidsPriceData);
    List<Float> bidsVolumeData = getVolumeData(bitcoiniumOrderbook.getBids());
    Collections.reverse(bidsVolumeData);

    xAxisBidData = bidsPriceData;
    yAxisBidData = bidsVolumeData;

    // Asks Series
    xAxisAskData = getPriceData(bitcoiniumOrderbook.getAsks());
    yAxisAskData = getVolumeData(bitcoiniumOrderbook.getAsks());

    // /////////////////////////////////
  }

  private static List<Float> getPriceData(CondensedOrder[] condensedOrders) {

    List<Float> priceData = new ArrayList<Float>();
    for (int i = 0; i < condensedOrders.length; i++) {
      priceData.add(condensedOrders[i].getPrice().floatValue());
    }
    return priceData;
  }

  private static List<Float> getVolumeData(CondensedOrder[] condensedOrders) {

    List<Float> volumeData = new ArrayList<Float>();
    for (int i = 0; i < condensedOrders.length; i++) {
      volumeData.add(condensedOrders[i].getVolume().floatValue());
    }
    return volumeData;
  }
}
