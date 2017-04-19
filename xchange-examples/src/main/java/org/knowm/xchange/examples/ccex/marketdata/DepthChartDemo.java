package org.knowm.xchange.examples.ccex.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ccex.CCEXExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Demonstrate requesting OrderBook from C-CEX and plotting it using XChart.
 */
public class DepthChartDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 1 Bitstamp exchange API using default settings
    Exchange ccexExchange = ExchangeFactory.INSTANCE.createExchange(CCEXExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = ccexExchange.getMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.XAUR_BTC);

    System.out.println("received data.");

    for (LimitOrder limitOrder : orderBook.getBids()) {
      System.out.println(limitOrder.getType() + " " + limitOrder.getCurrencyPair() + " Limit price: " + limitOrder.getLimitPrice() + " Amount: "
          + limitOrder.getTradableAmount());
    }

    for (LimitOrder limitOrder : orderBook.getAsks()) {
      System.out.println(limitOrder.getType() + " " + limitOrder.getCurrencyPair() + " Limit price: " + limitOrder.getLimitPrice() + " Amount: "
          + limitOrder.getTradableAmount());
    }

    System.out.println("plotting...");

    // Create Chart
    XYChart chart = new XYChartBuilder().width(800).height(600).title("C-CEX Order Book - Xaurum").xAxisTitle("BTC").yAxisTitle("Amount").build();

    // Customize Chart
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);

    // BIDS
    List<Number> xData = new ArrayList<>();
    List<Number> yData = new ArrayList<>();
    BigDecimal accumulatedBidUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getBids()) {
      xData.add(limitOrder.getLimitPrice());
      accumulatedBidUnits = accumulatedBidUnits.add(limitOrder.getTradableAmount());
      yData.add(accumulatedBidUnits);
    }
    Collections.reverse(xData);
    Collections.reverse(yData);

    // Bids Series
    XYSeries series = chart.addSeries("bids", xData, yData);
    series.setMarker(SeriesMarkers.NONE);

    // ASKS
    xData = new ArrayList<>();
    yData = new ArrayList<>();
    BigDecimal accumulatedAskUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getAsks()) {
      xData.add(limitOrder.getLimitPrice());
      accumulatedAskUnits = accumulatedAskUnits.add(limitOrder.getTradableAmount());
      yData.add(accumulatedAskUnits);
    }

    // Asks Series
    series = chart.addSeries("asks", xData, yData);
    series.setMarker(SeriesMarkers.NONE);

    new SwingWrapper(chart).displayChart();

  }

}
