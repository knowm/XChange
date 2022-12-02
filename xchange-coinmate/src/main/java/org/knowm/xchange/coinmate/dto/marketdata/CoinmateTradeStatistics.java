/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateTradeStatistics {

  private final BigDecimal lastRealizedTrade;
  private final BigDecimal todaysOpen;
  private final BigDecimal dailyChange;
  private final BigDecimal volume24Hours;
  private final BigDecimal high24hours;
  private final BigDecimal low24hours;

  public CoinmateTradeStatistics(
      @JsonProperty("lastRealizedTrade") BigDecimal lastRealizedTrade,
      @JsonProperty("todaysOpen") BigDecimal todaysOpen,
      @JsonProperty("dailyChange") BigDecimal dailyChange,
      @JsonProperty("volume24Hours") BigDecimal volume24Hours,
      @JsonProperty("high24hours") BigDecimal high24hours,
      @JsonProperty("low24hours") BigDecimal low24hours) {
    this.lastRealizedTrade = lastRealizedTrade;
    this.todaysOpen = todaysOpen;
    this.dailyChange = dailyChange;
    this.volume24Hours = volume24Hours;
    this.high24hours = high24hours;
    this.low24hours = low24hours;
  }

  public BigDecimal getLastRealizedTrade() {
    return lastRealizedTrade;
  }

  public BigDecimal getTodaysOpen() {
    return todaysOpen;
  }

  public BigDecimal getDailyChange() {
    return dailyChange;
  }

  public BigDecimal getVolume24Hours() {
    return volume24Hours;
  }

  public BigDecimal getHigh24hours() {
    return high24hours;
  }

  public BigDecimal getLow24hours() {
    return low24hours;
  }
}
