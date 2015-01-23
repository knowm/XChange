package com.xeiam.xchange.examples.bitstamp.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitstamp.BitstampExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.SwingWrapper;

/**
 * Demonstrate requesting OrderBook from Bitstamp and plotting it using XChart.
 */
public class DepthChartDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 1 Bitstamp exchange API using default settings
    Exchange bitstampExchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = bitstampExchange.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println("received data.");

    System.out.println("plotting...");

    // Create Chart
    Chart chart = new Chart(800, 500);

    // Customize Chart
    chart.setChartTitle("Bitstamp Order Book");
    chart.setYAxisTitle("BTC");
    chart.setXAxisTitle("USD");
    chart.getStyleManager().setChartType(ChartType.Area);

    // BIDS
    List<Number> xData = new ArrayList<Number>();
    List<Number> yData = new ArrayList<Number>();
    BigDecimal accumulatedBidUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getBids()) {
      if (limitOrder.getLimitPrice().doubleValue() > 10) {
        xData.add(limitOrder.getLimitPrice());
        accumulatedBidUnits = accumulatedBidUnits.add(limitOrder.getTradableAmount());
        yData.add(accumulatedBidUnits);
      }
    }
    Collections.reverse(xData);
    Collections.reverse(yData);

    // Bids Series
    Series series = chart.addSeries("bids", xData, yData);
    series.setMarker(SeriesMarker.NONE);

    // ASKS
    xData = new ArrayList<Number>();
    yData = new ArrayList<Number>();
    BigDecimal accumulatedAskUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getAsks()) {
      if (limitOrder.getLimitPrice().doubleValue() < 1000) {
        xData.add(limitOrder.getLimitPrice());
        accumulatedAskUnits = accumulatedAskUnits.add(limitOrder.getTradableAmount());
        yData.add(accumulatedAskUnits);
      }
    }

    // Asks Series
    series = chart.addSeries("asks", xData, yData);
    series.setMarker(SeriesMarker.NONE);

    new SwingWrapper(chart).displayChart();

  }

}
