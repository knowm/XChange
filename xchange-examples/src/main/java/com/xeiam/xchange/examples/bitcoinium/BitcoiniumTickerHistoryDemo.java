package com.xeiam.xchange.examples.bitcoinium;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchart.ChartBuilder_XY;
import org.knowm.xchart.Chart_XY;
import org.knowm.xchart.Series_XY;
import org.knowm.xchart.Series_XY.ChartXYSeriesRenderStyle;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.style.Styler.LegendPosition;
import org.knowm.xchart.internal.style.markers.SeriesMarkers;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataServiceRaw;

/**
 * Demonstrates plotting an OrderBook with XChart
 *
 * @author timmolter
 */
public class BitcoiniumTickerHistoryDemo {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    // exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    System.out.println(exchangeSpecification.toString());
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the public polling market data feed (no authentication)
    BitcoiniumMarketDataServiceRaw bitcoiniumMarketDataService = (BitcoiniumMarketDataServiceRaw) bitcoiniumExchange.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the latest order book data for BTC/USD - BITSTAMP
    BitcoiniumTickerHistory bitcoiniumTickerHistory = bitcoiniumMarketDataService.getBitcoiniumTickerHistory("BTC", "BITSTAMP_USD", "THIRTY_DAYS");

    System.out.println(bitcoiniumTickerHistory.toString());

    List<Date> xAxisData = new ArrayList<Date>();
    List<Float> yAxisData = new ArrayList<Float>();
    for (int i = 0; i < bitcoiniumTickerHistory.getCondensedTickers().length; i++) {

      BitcoiniumTicker bitcoiniumTicker = bitcoiniumTickerHistory.getCondensedTickers()[i];

      Date timestamp = new Date(bitcoiniumTicker.getTimestamp());
      float price = bitcoiniumTicker.getLast().floatValue();
      System.out.println(timestamp + ": " + price);
      xAxisData.add(timestamp);
      yAxisData.add(price);
    }

    // Create Chart
    Chart_XY chart = new ChartBuilder_XY().width(800).height(600).title("Bitstamp Price vs. Date").xAxisTitle("Date").yAxisTitle("Price").build();

    // Customize Chart
    chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
    chart.getStyler().setDefaultSeriesRenderStyle(ChartXYSeriesRenderStyle.Area);

    Series_XY series = chart.addSeries("Bitcoinium USD/BTC", xAxisData, yAxisData);
    series.setMarker(SeriesMarkers.NONE);

    new SwingWrapper(chart).displayChart();
  }
}
