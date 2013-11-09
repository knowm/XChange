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
package com.xeiam.xchange.btce.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Raphael Voellmy
 */
public class BTCETradeHistoryResult {

  private final String pair;
  private final Type type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final Long orderId;
  /** reflects who created original order. True means that you opened the order and then someone completely bought/sold it. False means you bought/sold from someone else's order. */
  private final int isYourOrder;
  private final Long timestamp;

  /**
   * Constructor
   * 
   * @param timestamp
   * @param isYourOrder
   * @param orderId
   * @param rate
   * @param amount
   * @param type
   * @param pair
   */
  public BTCETradeHistoryResult(@JsonProperty("timestamp") Long timestamp, @JsonProperty("is_your_order") int isYourOrder, @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("order_id") Long orderId, @JsonProperty("type") Type type, @JsonProperty("pair") String pair) {

    this.timestamp = timestamp;
    this.isYourOrder = isYourOrder;
    this.orderId = orderId;
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

  public Long getTimestamp() {

    return timestamp;
  }

  public Long getOrderId() {

    return orderId;
  }

  public boolean isYourOrder() {

    return isYourOrder == 1;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BTCEOwnTransaction[pair=''{0}'', type={1}, amount={2}, rate={3}, timestamp={4}, orderId={5}, isYourOrder={6}]", pair, type, amount, rate, timestamp, orderId,
        isYourOrder);
  }

  public static enum Type {
    buy, sell
  }
}
