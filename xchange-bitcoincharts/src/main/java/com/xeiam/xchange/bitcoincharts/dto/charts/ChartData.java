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
package com.xeiam.xchange.bitcoincharts.dto.charts;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ChartData {

  private final String date;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal volume;
  private final BigDecimal volumeCurrency;
  private final BigDecimal weightedPrice;

  public ChartData(ArrayList rawData) {

    this.date = String.valueOf(rawData.get(0));
    this.open = new BigDecimal(String.valueOf(rawData.get(1)));
    this.high = new BigDecimal(String.valueOf(rawData.get(2)));
    this.low = new BigDecimal(String.valueOf(rawData.get(3)));
    this.close = new BigDecimal(String.valueOf(rawData.get(4)));
    this.volume = new BigDecimal(String.valueOf(rawData.get(5)));
    this.volumeCurrency = new BigDecimal(String.valueOf(rawData.get(6)));
    this.weightedPrice = new BigDecimal(String.valueOf(rawData.get(7)));
  }

  public String getDate() {

    return date;
  }

  public BigDecimal getOpen() {

    return open;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getClose() {

    return close;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getVolumeCurrency() {

    return volumeCurrency;
  }

  public BigDecimal getWeightedPrice() {

    return weightedPrice;
  }

  @Override
  public String toString() {

    return "ChartData{" + "date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", volumeCurrency=" + volumeCurrency + ", weightedPrice="
        + weightedPrice + '}';
  }
}
