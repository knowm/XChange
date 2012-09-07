/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.dto;

import java.math.BigDecimal;

/**
 * Data object representing an order
 */
public class Order {

  public enum OrderType {

    BID,
    ASK
  }

  /**
   * Order type i.e. bid or ask
   */
  private OrderType type;

  /**
   * Amount to be ordered / amount that was ordered
   */
  private BigDecimal tradableAmount;

  /**
   * An identifier that uniquely identifies the tradable
   */
  private String tradableIdentifier;

  /**
   * The currency used to settle the market order transaction
   */
  private String transactionCurrency;

  public OrderType getType() {
    return type;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public BigDecimal getTradableAmount() {
    return tradableAmount;
  }

  public void setTradableAmount(BigDecimal tradableAmount) {
    this.tradableAmount = tradableAmount;
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
    return "Order [type=" + type + ", tradableAmount=" + tradableAmount + ", tradableIdentifier=" + tradableIdentifier + ", transactionCurrency=" + transactionCurrency + "]";
  }
}
