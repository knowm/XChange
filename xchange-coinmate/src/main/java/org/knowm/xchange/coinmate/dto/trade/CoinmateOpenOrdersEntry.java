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
package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Martin Stachon */
public class CoinmateOpenOrdersEntry {

  private final long id;
  private final long timestamp;
  private final String type;
  private final String currencyPair;
  private final BigDecimal price;
  private final BigDecimal amount;

  public CoinmateOpenOrdersEntry(
      @JsonProperty("id") long id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("type") String type,
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {

    this.id = id;
    this.timestamp = timestamp;
    this.type = type;
    this.currencyPair = currencyPair;
    this.price = price;
    this.amount = amount;
  }

  /** @return the id */
  public long getId() {
    return id;
  }

  /** @return the timestamp */
  public long getTimestamp() {
    return timestamp;
  }

  /** @return the type */
  public String getType() {
    return type;
  }

  /** @return the currency pair */
  public String getCurrencyPair() {
    return currencyPair;
  }

  /** @return the price */
  public BigDecimal getPrice() {
    return price;
  }

  /** @return the amount */
  public BigDecimal getAmount() {
    return amount;
  }
}
