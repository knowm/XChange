package org.knowm.xchange.examples.bitcoinium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinium.BitcoiniumExchange;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook.CondensedOrder;
import org.knowm.xchange.bitcoinium.service.BitcoiniumMarketDataServiceRaw;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Demonstrates plotting an OrderBook with XChart
 *
 * @author timmolter
 */
public class BitcoiniumRealtimeOrderbookDemo {

  public static final String BIDS_SERIES_NAME = "bids";
  public static final String ASKS_SERIES_NAME = "asks";
  XYChart chart;
  BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService;
  List<Float> xAxisBidData;
  List<Float> yAxisBidData;
  List<Float> xAxisAskData;
  List<Float> yAxisAskData;

  public static void main(String[] args) throws Exception {

    final BitcoiniumRealtimeOrderbookDemo bitcoiniumRealtimeTickerDemo =
        new BitcoiniumRealtimeOrderbookDemo();
    bitcoiniumRealtimeTickerDemo.go();
  }

  private static List<Float> getPriceData(CondensedOrder[] condensedOrders) {

    List<Float> priceData = new ArrayList<>();
    for (int i = 0; i < condensedOrders.length; i++) {
      priceData.add(condensedOrders[i].getPrice().floatValue());
    }
    return priceData;
  }

  private static List<Float> getVolumeData(CondensedOrder[] condensedOrders) {

    List<Float> volumeData = new ArrayList<>();
    for (int i = 0; i < condensedOrders.length; i++) {
      volumeData.add(condensedOrders[i].getVolume().floatValue());
    }
    return volumeData;
  }

  private void go() throws IOException {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BitcoiniumExchange.class);
    // exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    System.out.println(exchangeSpecification.toString());
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public market data feed (no authentication)
    bitcoiniumMarketDataService =
        (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getMarketDataService();

    // Setup the panel
    final XChartPanel<XYChart> chartPanel = buildPanel();
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(
        new Runnable() {

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
    TimerTask chartUpdaterTask =
        new TimerTask() {

          @Override
          public void run() {

            try {
              updateData();
              // update chart
              chart.updateXYSeries(BIDS_SERIES_NAME, xAxisBidData, yAxisBidData, null);
              chart.updateXYSeries(ASKS_SERIES_NAME, xAxisAskData, yAxisAskData, null);
              chartPanel.revalidate();
              chartPanel.repaint();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        };

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(chartUpdaterTask, 0, 10000); // every ten
    // seconds

  }

  public XChartPanel<XYChart> buildPanel() throws IOException {

    System.out.println("fetching data...");

    updateData();

    // create chart
    chart =
        new XYChartBuilder()
            .width(800)
            .height(400)
            .title("Real-time Bitcoinium Order Book - BITSTAMP_BTC_USD")
            .xAxisTitle("BTC")
            .yAxisTitle("USD")
            .build();

    // Customize Chart
    chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);

    // add series
    XYSeries series = chart.addSeries(BIDS_SERIES_NAME, xAxisBidData, yAxisBidData);
    series.setMarker(SeriesMarkers.NONE);
    series = chart.addSeries(ASKS_SERIES_NAME, xAxisAskData, yAxisAskData);
    series.setMarker(SeriesMarkers.NONE);

    return new XChartPanel<>(chart);
  }

  private void updateData() throws IOException {

    // /////////////////////////////////
    // Get the latest order book data for BTC/USD - MTGOX
    BitcoiniumOrderbook bitcoiniumOrderbook =
        bitcoiniumMarketDataService.getBitcoiniumOrderbook("BTC", "BITSTAMP_USD", "TEN_PERCENT");

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
}
