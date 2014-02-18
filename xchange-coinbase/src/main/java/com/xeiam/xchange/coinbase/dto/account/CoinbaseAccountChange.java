package com.xeiam.xchange.coinbase.dto.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser.CoinbaseUserInfo;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.utils.jackson.ISO8601DateDeserializer;

public class CoinbaseAccountChange {

  private final String id;
  private final Date createdAt;
  private final String transactionId;
  private final boolean confirmed;
  private final CoinbaseCache cache;
  private final CoinbaseAmount amount;

  public CoinbaseAccountChange(@JsonProperty("id") final String id, @JsonProperty("created_at") @JsonDeserialize(using=ISO8601DateDeserializer.class) final Date createdAt, @JsonProperty("transaction_id") final String transactionId,
      @JsonProperty("confirmed") final boolean confirmed, @JsonProperty("cache") final CoinbaseCache cache, @JsonProperty("amount") final CoinbaseAmount amount) {

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

  public CoinbaseAmount getAmount() {

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

    public CoinbaseCache(@JsonProperty("notes_present") final boolean notesPresent, @JsonProperty("category") final CoinbaseAccountChangeCategory category, @JsonProperty("other_user") final CoinbaseUserInfo otherUser) {

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
