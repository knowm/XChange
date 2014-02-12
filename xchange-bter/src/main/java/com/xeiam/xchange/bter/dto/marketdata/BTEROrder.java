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
package com.xeiam.xchange.bter.dto.marketdata;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTEROrder {

  private final String pair;
  private final Type type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final Long timestampCreated;
  /** 0: active; 1: ??; 2: cancelled */
  private final int status;

  /**
   * Constructor
   * 
   * @param status
   * @param timestampCreated
   * @param rate
   * @param amount
   * @param type
   * @param pair
   */
  public BTEROrder(@JsonProperty("status") int status, @JsonProperty("timestamp_created") Long timestampCreated, @JsonProperty("rate") BigDecimal rate, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("type") Type type, @JsonProperty("pair") String pair) {

    this.status = status;
    this.timestampCreated = timestampCreated;
    this.rate = rate;
    this.amount = amount;
    this.type = type;
    this.pair = pair;
  }

  public String getPair() {

    return pair;
  }

  public Type getType() {

    return type;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public Long getTimestampCreated() {

    return timestampCreated;
  }

  public int getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BTEROrder[pair=''{0}'', type={1}, amount={2}, rate={3}, timestampCreated={4}, status={5}]", pair, type, amount, rate, timestampCreated, status);
  }

  public static enum Type {
    buy, sell
  }
}
