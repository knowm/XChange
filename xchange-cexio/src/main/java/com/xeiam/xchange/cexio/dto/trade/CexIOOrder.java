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
package com.xeiam.xchange.cexio.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox
 * Since: 2/5/14
 */
public class CexIOOrder {

  private final int id;
  private final long time;
  private final Type type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal pending;
  private final String errorMessage;

  /** non-JSON fields */
  private String tradableIdentifier;
  private String transactionCurrency;

  /**
   * Constructor
   * 
   * @param id
   * @param time
   * @param type
   * @param price
   * @param amount
   * @param pending
   */
  public CexIOOrder(@JsonProperty("id") int id, @JsonProperty("time") long time, @JsonProperty("type") Type type, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("pending") BigDecimal pending, @JsonProperty("error") String errorMessage) {

    this.id = id;
    this.time = time;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.pending = pending;
    this.errorMessage = errorMessage;
  }

  public int getId() {

    return id;
  }

  public long getTime() {

    return time;
  }

  public Type getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getPending() {

    return pending;
  }

  public String getErrorMessage() {

    return errorMessage;
  }

  public String getTradableIdentifier() {

    return tradableIdentifier;
  }

  public void setTradableIdentifier(String tradableIdentifier) {

    this.tradableIdentifier = tradableIdentifier;
  }

  public String getTransactionCurrency() {

    return transactionCurrency;
  }

  public void setTransactionCurrency(String transactionCurrency) {

    this.transactionCurrency = transactionCurrency;
  }

  @Override
  public String toString() {

    return errorMessage != null ? errorMessage : String.format("Order{id=%s, time=%s, type=%s, price=%s, amount=%s, pending=%s}", id, time, type, price, amount, pending);
  }

  public static enum Type {

    buy, sell
  }

}
