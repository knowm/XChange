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
package com.xeiam.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexOfferStatusResponse {

  private final int id;
  private final String currency;
  private final BigDecimal rate;
  private final int period;
  private final String direction;
  private final String type;
  private final float timestamp;
  private final boolean isLive;
  private final boolean isCancelled;
  private final BigDecimal originalAmount;
  private final BigDecimal remainingAmount;
  private final BigDecimal executedAmount;
  
  public BitfinexOfferStatusResponse(@JsonProperty("id") int id, @JsonProperty("currency") String currency, @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("period") int period, @JsonProperty("direction") String direction, @JsonProperty("type") String type, 
      @JsonProperty("timestamp") float timestamp, @JsonProperty("is_live") boolean isLive, @JsonProperty("is_cancelled") boolean isCancelled, 
      @JsonProperty("original_amount") BigDecimal originalAmount, @JsonProperty("remaining_amount") BigDecimal remainingAmount, 
      @JsonProperty("executed_amount") BigDecimal executedAmount) {
    
    this.id = id;
    this.currency = currency;
    this.rate = rate;
    this.period = period;
    this.direction = direction;
    this.type = type;
    this.timestamp = timestamp;
    this.isLive = isLive;
    this.isCancelled = isCancelled;
    this.originalAmount = originalAmount;
    this.remainingAmount = remainingAmount;
    this.executedAmount = executedAmount;
  }

  public int getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public int getPeriod() {
    return period;
  }

  public String getDirection() {
    return direction;
  }

  public String getType() {
    return type;
  }

  public float getTimestamp() {
    return timestamp;
  }

  public boolean isLive() {
    return isLive;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public BigDecimal getRemainingAmount() {
    return remainingAmount;
  }

  public BigDecimal getExecutedAmount() {
    return executedAmount;
  }

  @Override
  public String toString() {
    return "BitfinexOfferStatusResponse [id=" + id + ", currency=" + currency + ", rate=" + rate + ", period=" + period + ", direction=" + direction + ", type=" + type + ", timestamp=" + timestamp + ", isLive=" + isLive + ", isCancelled=" + isCancelled + ", originalAmount=" + originalAmount + ", remainingAmount=" + remainingAmount + ", executedAmount=" + executedAmount + "]";
  }
  
}
