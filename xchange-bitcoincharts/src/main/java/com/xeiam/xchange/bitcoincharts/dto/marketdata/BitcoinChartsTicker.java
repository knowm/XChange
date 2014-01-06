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
package com.xeiam.xchange.bitcoincharts.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitcoinChartsTicker {

  private final BigDecimal ask;
  private final BigDecimal avg;
  private final BigDecimal bid;
  private final BigDecimal close;
  private final String currency;
  private final BigDecimal currencyVolume;
  private final BigDecimal high;
  private final long latestTrade;
  private final BigDecimal low;
  private final String symbol;
  private final BigDecimal volume;

  /**
   * Constructor
   * 
   * @param ask
   * @param avg
   * @param bid
   * @param close
   * @param currency
   * @param currencyVolume
   * @param high
   * @param latestTrade
   * @param low
   * @param symbol
   * @param volume
   */
  public BitcoinChartsTicker(@JsonProperty("ask") BigDecimal ask, @JsonProperty("avg") BigDecimal avg, @JsonProperty("bid") BigDecimal bid, @JsonProperty("close") BigDecimal close,
      @JsonProperty("currency") String currency, @JsonProperty("currency_volume") BigDecimal currencyVolume, @JsonProperty("high") BigDecimal high, @JsonProperty("latest_trade") long latestTrade,
      @JsonProperty("low") BigDecimal low, @JsonProperty("symbol") String symbol, @JsonProperty("volume") BigDecimal volume) {

    this.ask = ask;
    this.avg = avg;
    this.bid = bid;
    this.close = close;
    this.currency = currency;
    this.currencyVolume = currencyVolume;
    this.high = high;
    this.latestTrade = latestTrade;
    this.low = low;
    this.symbol = symbol;
    this.volume = volume;
  }

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

  public long getLatestTrade() {

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

    return "BitcoinChartsTickers [ask=" + ask + ", avg=" + avg + ", bid=" + bid + ", close=" + close + ", currency=" + currency + ", currencyVolume=" + currencyVolume + ", high=" + high
        + ", latestTrade=" + latestTrade + ", low=" + low + ", symbol=" + symbol + ", volume=" + volume + "]";
  }

}
