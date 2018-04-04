package org.knowm.xchange.luno.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class LunoPendingTransactions {

  public final String id;
  public final boolean defaultAccount;
  private final PendingTransaction[] pending;

  public LunoPendingTransactions(
      @JsonProperty(value = "id", required = true) String id,
      @JsonProperty(value = "is_default") boolean defaultAccount,
      @JsonProperty(value = "pending", required = true) PendingTransaction[] pending) {
    this.id = id;
    this.defaultAccount = defaultAccount;
    this.pending = pending != null ? pending : new PendingTransaction[0];
  }

  @Override
  public String toString() {
    return "LunoAccountTransactions [id="
        + id
        + ", defaultAccount="
        + defaultAccount
        + ", pending("
        + pending.length
        + ")="
        + Arrays.toString(pending)
        + "]";
  }

  public PendingTransaction[] getTransactions() {
    PendingTransaction[] copy = new PendingTransaction[pending.length];
    System.arraycopy(pending, 0, copy, 0, pending.length);
    return copy;
  }

  public static class PendingTransaction {
    public final long timestamp;
    public final BigDecimal balance;
    public final BigDecimal available;
    public final BigDecimal balanceDelta;
    public final BigDecimal availableDelta;
    public final String currency;
    public final String description;

    public PendingTransaction(
        @JsonProperty(value = "timestamp", required = true) long timestamp,
        @JsonProperty(value = "balance", required = true) BigDecimal balance,
        @JsonProperty(value = "available", required = true) BigDecimal available,
        @JsonProperty(value = "balance_delta", required = true) BigDecimal balanceDelta,
        @JsonProperty(value = "available_delta", required = true) BigDecimal availableDelta,
        @JsonProperty(value = "currency", required = true) String currency,
        @JsonProperty(value = "description", required = false) String description) {
      this.timestamp = timestamp;
      this.balance = balance;
      this.available = available;
      this.balanceDelta = balanceDelta;
      this.availableDelta = availableDelta;
      this.currency = currency;
      this.description = description;
    }

    public Date getTimestamp() {
      return new Date(timestamp);
    }

    @Override
    public String toString() {
      return "PendingTransaction [timestamp="
          + getTimestamp()
          + ", balance="
          + balance
          + ", available="
          + available
          + ", balanceDelta="
          + balanceDelta
          + ", availableDelta="
          + availableDelta
          + ", currency="
          + currency
          + ", description="
          + description
          + "]";
    }
  }
}
