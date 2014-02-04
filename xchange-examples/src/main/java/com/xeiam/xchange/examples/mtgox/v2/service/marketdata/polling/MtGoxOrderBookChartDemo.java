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
package com.xeiam.xchange.examples.mtgox.v2.service.marketdata.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v2.MtGoxExchange;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.SwingWrapper;

/**
 * Demonstrates plotting an OrderBook with XChart
 *
 * @author timmolter
 */
public class MtGoxOrderBookChartDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = mtGox.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(Currencies.BTC, Currencies.USD);

    System.out.println("received data.");

    System.out.println("plotting...");

    // Create Chart
    Chart chart = new Chart(800, 500);

    // Customize Chart
    chart.setChartTitle("MtGox Order Book");
    chart.setYAxisTitle("BTC");
    chart.setXAxisTitle("USD");
    chart.getStyleManager().setChartType(ChartType.Area);

    // BIDS
    List<Number> xData = new ArrayList<Number>();
    List<Number> yData = new ArrayList<Number>();
    BigDecimal accumulatedBidUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getBids()) {
      xData.add(limitOrder.getLimitPrice().getAmount());
      accumulatedBidUnits = accumulatedBidUnits.add(limitOrder.getTradableAmount());
      yData.add(accumulatedBidUnits);
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
      xData.add(limitOrder.getLimitPrice().getAmount());
      accumulatedAskUnits = accumulatedAskUnits.add(limitOrder.getTradableAmount());
      yData.add(accumulatedAskUnits);
    }

    // Asks Series
    series = chart.addSeries("asks", xData, yData);
    series.setMarker(SeriesMarker.NONE);

    new SwingWrapper(chart).displayChart();

  }
}
