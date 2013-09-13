/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package org.xchange.kraken.dto.marketdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from Kraken
 */
public class KrakenTicker {

  private final BigDecimal[] ask; // ask array(<price>, <lot volume>),
  private final BigDecimal[] bid; // bid array(<price>, <lot volume>),
  private final BigDecimal[] close; // last trade closed array(<price>, <lot volume>),
  private final BigDecimal[] volume; // volume array(<today>, <last 24 hours>),
  private final BigDecimal[] volumeAvg; // volume weighted average price array(<today>, <last 24 hours>),
  private final BigDecimal[] trades; // number of trades array(<today>, <last 24 hours>),
  private final BigDecimal[] low; // low array(<today>, <last 24 hours>),
  private final BigDecimal[] high; // high array(<today>, <last 24 hours>),
  private final BigDecimal open; // today's opening price

  /**
   * Constructor
   * 
   * @param ask
   * @param bid
   * @param close
   * @param volume
   * @param volumeAvg
   * @param trades
   * @param low
   * @param high
   * @param open
   * 
   */

  public KrakenTicker(@JsonProperty("a") BigDecimal[] ask, @JsonProperty("b") BigDecimal[] bid, @JsonProperty("c") BigDecimal[] close, @JsonProperty("v") BigDecimal[] volume,
      @JsonProperty("p") BigDecimal[] volumeAvg, @JsonProperty("t") BigDecimal[] trades, @JsonProperty("l") BigDecimal[] low, @JsonProperty("h") BigDecimal[] high, @JsonProperty("o") BigDecimal open) {

    this.ask = ask;
    this.bid = bid;
    this.close = close;
    this.volume = volume;
    this.volumeAvg = volumeAvg;
    this.trades = trades;
    this.low = low;
    this.high = high;
    this.open = open;

  }
  
  public BigDecimal[] getAsk() {
  
    return ask;
  }
  
  public BigDecimal[] getBid() {
  
    return bid;
  }

  public BigDecimal[] getClose() {
  
    return close;
  }
 
  public BigDecimal[] getVolume() {
  
    return volume;
  }
  
  public BigDecimal[] getVolumeAvg() {
  
    return volumeAvg;
  }
 
  public BigDecimal[] getTrades() {
  
    return trades;
  }

  public BigDecimal[] getLow() {
  
    return low;
  }
  
  public BigDecimal[] getHigh() {
  
    return high;
  }
  
  public BigDecimal getOpen() {
  
    return open;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("KrakenTicker [")
      .append("ask=" + Arrays.toString(ask) + ", ")
      .append("bid=" + Arrays.toString(bid) + ", ")
      .append("high=" + Arrays.toString(high) + ", ")
      .append("low=" + Arrays.toString(low) + ", ")
      .append("trades=" + Arrays.toString(trades) + ", ")
      .append("volume=" + Arrays.toString(volume) + ", ")
      .append("volumeAvg=" + Arrays.toString(volumeAvg) + ", ")
      .append("open=" + open + ", ")
      .append("close=" + Arrays.toString(close) + "]");

    return sb.toString();
  }
}
