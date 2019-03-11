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
public class CoinmateTransactionHistoryEntry {

  private final long transactionId;
  private final long timestamp;
  private final String transactionType;
  private final BigDecimal amount;
  private final String amountCurrency;
  private final BigDecimal price;
  private final String priceCurrency;
  private final BigDecimal fee;
  private final String feeCurrency;
  private final String description;
  private final String status;
  private final long orderId; // ?

  public CoinmateTransactionHistoryEntry(
      @JsonProperty("transactionId") long transactionId,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("transactionType") String transactionType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amountCurrency") String amountCurrency,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("priceCurrency") String priceCurrency,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("feeCurrency") String feeCurrency,
      @JsonProperty("description") String description,
      @JsonProperty("status") String status,
      @JsonProperty("orderId") long orderId) {

    this.transactionId = transactionId;
    this.timestamp = timestamp;
    this.transactionType = transactionType;
    this.amount = amount;
    this.amountCurrency = amountCurrency;
    this.price = price;
    this.priceCurrency = priceCurrency;
    this.fee = fee;
    this.feeCurrency = feeCurrency;
    this.description = description;
    this.status = status;
    this.orderId = orderId;
  }

  public long getTransactionId() {
    return transactionId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getAmountCurrency() {
    return amountCurrency;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getPriceCurrency() {
    return priceCurrency;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public long getOrderId() {
    return orderId;
  }
}
