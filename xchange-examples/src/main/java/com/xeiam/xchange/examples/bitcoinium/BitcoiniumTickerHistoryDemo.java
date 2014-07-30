package com.xeiam.xchange.examples.bitcoinium;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
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
import com.xeiam.xchart.SwingWrapper;

/**
 * Demonstrates plotting an OrderBook with XChart
 * 
 * @author timmolter
 */
public class BitcoiniumTickerHistoryDemo {

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
    BitcoiniumTickerHistory bitcoiniumTickerHistory = bitcoiniumMarketDataService.getBitcoiniumTickerHistory(Currencies.BTC, "BITSTAMP_USD", "2M");

    System.out.println(bitcoiniumTickerHistory.toString());

    List<Date> xAxisData = new ArrayList<Date>();
    List<Double> yAxisData = new ArrayList<Double>();
    long runninTimestamp = bitcoiniumTickerHistory.getBaseTimestamp();
    for (int i = 0; i < bitcoiniumTickerHistory.getPriceHistoryList().size(); i++) {

      Date timestamp = new Date((runninTimestamp += bitcoiniumTickerHistory.getTimeStampOffsets().get(i)) * 1000);
      double price = bitcoiniumTickerHistory.getPriceHistoryList().get(i).doubleValue();
      System.out.println(timestamp + ": " + price);
      xAxisData.add(timestamp);
      yAxisData.add(price);
    }

    Chart chart = new ChartBuilder().chartType(ChartType.Area).width(800).height(600).title("MtGox Price vs. Date").xAxisTitle("Date").yAxisTitle("Price").build();
    chart.getStyleManager().setLegendPosition(LegendPosition.InsideNE);

    Series series = chart.addSeries("MtGox USD/BTC", xAxisData, yAxisData);
    series.setMarker(SeriesMarker.NONE);

    new SwingWrapper(chart).displayChart();
  }
}
