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
package com.xeiam.xchange.bitcoinaverage.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from BitcoinAverage
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public final class BitcoinAverageTicker {

  private final BigDecimal last;
  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal volume_percent;
  private final BigDecimal volume;
  private final String timestamp;

  /**
   * Constructor
   * 
   * @param bid
   * @param ask
   * @param volume
   * @param last
   * @param avg
   * @param timestamp
   */

  public BitcoinAverageTicker(@JsonProperty("ask") BigDecimal ask, @JsonProperty("bid") BigDecimal bid, @JsonProperty("volume_btc") BigDecimal volume, @JsonProperty("last") BigDecimal last,
      @JsonProperty("volume_percent") BigDecimal volume_percent, @JsonProperty("timestamp") String timestamp) {

    this.ask = ask;
    this.bid = bid;
    this.volume = volume;
    this.last = last;
    this.volume_percent = volume_percent;
    this.timestamp = timestamp;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getVolumePercent() {

    return volume_percent;
  }

  public Date getTimestamp() {

    try {
      // Parse the timestamp into a Date object
      return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault()).parse(timestamp);
    } catch (IllegalArgumentException e) {
      // Return current Date
      return new Date();
    } catch (ParseException e) {
      // Return current Date
      return new Date();
    }
  }

  @Override
  public String toString() {

    return "BitcoinAverageTicker [last=" + last + ", ask=" + ask + ", bid=" + bid + ", volume=" + volume + ", volume_percent=" + volume_percent + ", timestamp=" + timestamp + "]";

  }

}
