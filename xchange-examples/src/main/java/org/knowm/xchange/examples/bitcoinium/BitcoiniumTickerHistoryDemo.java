package org.knowm.xchange.examples.bitcoinium;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinium.BitcoiniumExchange;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import org.knowm.xchange.bitcoinium.service.BitcoiniumMarketDataServiceRaw;
import org.knowm.xchart.SwingWrapper;
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
public class BitcoiniumTickerHistoryDemo {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BitcoiniumExchange.class.getName());
    // exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    System.out.println(exchangeSpecification.toString());
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public market data feed (no authentication)
    BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService =
        (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getMarketDataService();

    System.out.println("fetching data...");

    // Get the latest order book data for BTC/USD - BITSTAMP
    BitcoiniumTickerHistory bitcoiniumTickerHistory =
        bitcoiniumMarketDataService.getBitcoiniumTickerHistory(
            "BTC", "BITSTAMP_USD", "THIRTY_DAYS");

    System.out.println(bitcoiniumTickerHistory.toString());

    List<Date> xAxisData = new ArrayList<>();
    List<Float> yAxisData = new ArrayList<>();
    for (int i = 0; i < bitcoiniumTickerHistory.getCondensedTickers().length; i++) {

      BitcoiniumTicker bitcoiniumTicker = bitcoiniumTickerHistory.getCondensedTickers()[i];

      Date timestamp = new Date(bitcoiniumTicker.getTimestamp());
      float price = bitcoiniumTicker.getLast().floatValue();
      System.out.println(timestamp + ": " + price);
      xAxisData.add(timestamp);
      yAxisData.add(price);
    }

    // Create Chart
    XYChart chart =
        new XYChartBuilder()
            .width(800)
            .height(600)
            .title("Bitstamp Price vs. Date")
            .xAxisTitle("Date")
            .yAxisTitle("Price")
            .build();

    // Customize Chart
    chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);

    XYSeries series = chart.addSeries("Bitcoinium USD/BTC", xAxisData, yAxisData);
    series.setMarker(SeriesMarkers.NONE);

    new SwingWrapper(chart).displayChart();
  }
}
