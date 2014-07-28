/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.cryptotrade.dto.account;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeTransactions extends CryptoTradeBaseResponse {

  private final List<CryptoTradeTransaction> transactions;

  private CryptoTradeTransactions(@JsonProperty("data") List<CryptoTradeTransaction> transactions, @JsonProperty("status") String status, @JsonProperty("error") String error) {

    super(status, error);
    this.transactions = transactions;
  }

  public List<CryptoTradeTransaction> getTransactions() {

    return transactions;
  }

  @Override
  public String toString() {

    return "CryptoTradeTransactions [transactions=" + transactions + "]";
  }

  public static class CryptoTradeTransaction {

    private final long id;
    private final long timestamp;
    private final String currency;
    private final String type;
    private final BigDecimal amount;
    private final String description;
    private final String status;

    private CryptoTradeTransaction(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp,
        @JsonProperty("pair") @JsonDeserialize(using = CurrencyPairDeserializer.class) String currency, @JsonProperty("type") String type, @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("rate") BigDecimal rate, @JsonProperty("desc") String description, @JsonProperty("status") String status) {

      this.id = id;
      this.timestamp = timestamp;
      this.currency = currency;
      this.type = type;
      this.amount = amount;
      this.description = description;
      this.status = status;
    }

    public long getId() {

      return id;
    }

    public long getTimestamp() {

      return timestamp;
    }

    public String getCurrency() {

      return currency;
    }

    public String getType() {

      return type;
    }

    public BigDecimal getAmount() {

      return amount;
    }

    public String getDescription() {

      return description;
    }

    public String getStatus() {

      return status;
    }

    @Override
    public String toString() {

      return "CryptoTradeTransaction [id=" + id + ", timestamp=" + timestamp + ", currencyPair=" + currency + ", type=" + type + ", amount=" + amount + ", description=" + description + ", status="
          + status + "]";
    }

  }

}
