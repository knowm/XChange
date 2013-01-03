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
package com.xeiam.xchange.bitcoincharts.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;

public class MarketData {
  private BigDecimal ask;
  private BigDecimal avg;
  private BigDecimal bid;
  private BigDecimal close;
  private String currency;
  @JsonProperty("currency_volume")
  private BigDecimal currencyVolume;
  private BigDecimal high;
  @JsonProperty("latest_trade")
  private BigDecimal latestTrade;
  private BigDecimal low;
  private String symbol;
  private BigDecimal volume;

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getClose() {
    return close;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getCurrencyVolume() {
    return currencyVolume;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLatestTrade() {
    return latestTrade;
  }

  public BigDecimal getLow() {
    return low;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "MarketData{" +
        "symbol='" + symbol + '\'' +
        ", currency='" + currency + '\'' +
        ", ask=" + ask +
        ", avg=" + avg +
        ", bid=" + bid +
        ", close=" + close +
        ", currencyVolume=" + currencyVolume +
        ", high=" + high +
        ", latestTrade=" + latestTrade +
        ", low=" + low +
        ", volume=" + volume +
        '}';
  }
}
