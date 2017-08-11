package org.knowm.xchange.examples.cryptopia.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Demonstrate requesting OrderBook from Cryptopia and plotting it using XChart.
 */
public class DepthChartDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Cryptopia exchange API using default settings
    Exchange cryptopia = ExchangeFactory.INSTANCE.createExchange(CryptopiaExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = cryptopia.getMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

    System.out.println("received data.");

    System.out.println("plotting...");

    // Create Chart
    XYChart chart = new XYChartBuilder().width(800).height(600).title("Cryptopia Order Book").xAxisTitle("BTC").yAxisTitle("ETH").build();

    // Customize Chart
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

    // BIDS
    List<Number> xData = new ArrayList<>();
    List<Number> yData = new ArrayList<>();
    BigDecimal accumulatedBidUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getBids()) {
      if (limitOrder.getLimitPrice().doubleValue() > 0.01) {
        xData.add(limitOrder.getLimitPrice());
        accumulatedBidUnits = accumulatedBidUnits.add(limitOrder.getTradableAmount());
        yData.add(accumulatedBidUnits);
      }
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
      if (limitOrder.getLimitPrice().doubleValue() < 1) {
        xData.add(limitOrder.getLimitPrice());
        accumulatedAskUnits = accumulatedAskUnits.add(limitOrder.getTradableAmount());
        yData.add(accumulatedAskUnits);
      }
    }

    // Asks Series
    series = chart.addSeries("asks", xData, yData);
    series.setMarker(SeriesMarkers.NONE);

    new SwingWrapper(chart).displayChart();
  }
}
