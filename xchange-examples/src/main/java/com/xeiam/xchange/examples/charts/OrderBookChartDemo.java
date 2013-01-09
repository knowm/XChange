/**
 * Copyright 2013 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchange.examples.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.SwingWrapper;

/**
 * Demonstrates plotting an OrderBook with XChart
 * 
 * @author timmolter
 * @create Jan 9, 2013
 */
public class OrderBookChartDemo {

  public static void main(String[] args) {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = mtGox.getPollingMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getPartialOrderBook(Currencies.BTC, Currencies.USD);

    // Create Chart
    Chart chart = new Chart(1000, 700);

    // Customize Chart
    chart.setTitle("MtGox Order Book");
    chart.setYAxisTitle("BTC");
    chart.setXAxisTitle("USD");

    // BIDS
    Collection<Number> xData = new ArrayList<Number>();
    Collection<Number> yData = new ArrayList<Number>();
    BigDecimal accumulatedBidUnits = new BigDecimal("0");
    for (LimitOrder limitOrder : orderBook.getBids()) {
      xData.add(limitOrder.getLimitPrice().getAmount());
      accumulatedBidUnits = accumulatedBidUnits.add(limitOrder.getTradableAmount());
      yData.add(accumulatedBidUnits);
    }

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
