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
package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexLendLevel {

  private final BigDecimal rate;
  private final BigDecimal amount;
  private final int period;
  private final float timestamp;
  private final String frr;

  /**
   * Constructor
   * 
   * @param rate
   * @param amount
   * @param period
   * @param timestamp
   * @param frr
   */
  public BitfinexLendLevel(@JsonProperty("rate") BigDecimal rate, @JsonProperty("amount") BigDecimal amount, @JsonProperty("period") int period, @JsonProperty("timestamp") float timestamp, @JsonProperty("frr") String frr) {

    this.rate = rate;
    this.amount = amount;
    this.period = period;
    this.timestamp = timestamp;
    this.frr = frr;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public int getPeriod() {
    return period;
  }

  public float getTimestamp() {
    return timestamp;
  }

  public String getFrr() {
    return frr;
  }

  @Override
  public String toString() {
    return "BitfinexLendLevel [rate=" + rate + ", amount=" + amount + ", period=" + period + ", timestamp=" + timestamp + ", frr=" + frr + "]";
  }
}
