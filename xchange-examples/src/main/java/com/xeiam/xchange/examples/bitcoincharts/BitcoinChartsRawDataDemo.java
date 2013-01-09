/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.bitcoincharts;

import com.xeiam.xchange.bitcoincharts.BitcoinCharts;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsFactory;
import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;

/**
 * Demonstrates using the REST proxy to get the raw deserialized JSON object from BitcoinCharts
 * 
 * @author timmolter
 * @create Jan 9, 2013
 */
public class BitcoinChartsRawDataDemo {

  public static void main(String[] args) {

    BitcoinCharts bitcoinCharts = BitcoinChartsFactory.createInstance();
    BitcoinChartsTicker[] marketData = bitcoinCharts.getMarketData();
    for (BitcoinChartsTicker data : marketData) {
      System.out.println(data.getSymbol() + ": " + data);
    }
  }
}
