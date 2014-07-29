package com.xeiam.xchange.examples.bitcoincharts;

import java.io.IOException;

import com.xeiam.xchange.bitcoincharts.BitcoinCharts;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsFactory;
import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;

/**
 * Demonstrates using the REST proxy to get the raw deserialized JSON object from BitcoinCharts
 * 
 * @author timmolter
 */
public class BitcoinChartsRawDataDemo {

  public static void main(String[] args) throws IOException {

    BitcoinCharts bitcoinCharts = BitcoinChartsFactory.createInstance();
    BitcoinChartsTicker[] marketData = bitcoinCharts.getMarketData();
    for (BitcoinChartsTicker data : marketData) {
      System.out.println(data.getSymbol() + ": " + data);
    }

  }
}
