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
package com.xeiam.xchange.coinbase.dto.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser.CoinbaseUserInfo;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.utils.jackson.ISO8601DateDeserializer;

/**
 * @author jamespedwards42
 */
public class CoinbaseAccountChange {

  private final String id;
  private final Date createdAt;
  private final String transactionId;
  private final boolean confirmed;
  private final CoinbaseCache cache;
  private final CoinbaseMoney amount;

  private CoinbaseAccountChange(@JsonProperty("id") final String id, @JsonProperty("created_at") @JsonDeserialize(using = ISO8601DateDeserializer.class) final Date createdAt,
      @JsonProperty("transaction_id") final String transactionId, @JsonProperty("confirmed") final boolean confirmed, @JsonProperty("cache") final CoinbaseCache cache,
      @JsonProperty("amount") final CoinbaseMoney amount) {

    this.id = id;
    this.createdAt = createdAt;
    this.transactionId = transactionId;
    this.confirmed = confirmed;
    this.cache = cache;
    this.amount = amount;
  }

  public String getId() {

    return id;
  }

  public Date getCreatedAt() {

    return createdAt;
  }

  public String getTransactionId() {

    return transactionId;
  }

  public boolean isConfirmed() {

    return confirmed;
  }

  public CoinbaseCache getCache() {

    return cache;
  }

  public CoinbaseMoney getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "AccountChange [id=" + id + ", createdAt=" + createdAt + ", transactionId=" + transactionId + ", confirmed=" + confirmed + ", cache=" + cache + ", amount=" + amount + "]";
  }

  public static class CoinbaseCache {

    private final boolean notesPresent;
    private final CoinbaseAccountChangeCategory category;
    private final CoinbaseUser otherUser;

    private CoinbaseCache(@JsonProperty("notes_present") final boolean notesPresent, @JsonProperty("category") final CoinbaseAccountChangeCategory category,
        @JsonProperty("other_user") final CoinbaseUserInfo otherUser) {

      this.notesPresent = notesPresent;
      this.category = category;
      this.otherUser = new CoinbaseUser(otherUser);
    }

    public boolean isNotesPresent() {

      return notesPresent;
    }

    public CoinbaseAccountChangeCategory getCategory() {

      return category;
    }

    public CoinbaseUser getOtherUser() {

      return otherUser;
    }

    @Override
    public String toString() {

      return "CoinbaseCache [notesPresent=" + notesPresent + ", category=" + category + ", otherUser=" + otherUser + "]";
    }
  }
}
