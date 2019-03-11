package org.knowm.xchange.luno.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class LunoAccountTransactions {

  public final String id;
  public final boolean defaultAccount;
  private final Transaction[] transactions;

  public LunoAccountTransactions(
      @JsonProperty(value = "id", required = true) String id,
      @JsonProperty(value = "is_default", required = true) boolean defaultAccount,
      @JsonProperty(value = "transactions", required = true) Transaction[] transactions) {
    this.id = id;
    this.defaultAccount = defaultAccount;
    this.transactions = transactions != null ? transactions : new Transaction[0];
  }

  @Override
  public String toString() {
    return "LunoAccountTransactions [id="
        + id
        + ", defaultAccount="
        + defaultAccount
        + ", transactions("
        + transactions.length
        + ")="
        + Arrays.toString(transactions)
        + "]";
  }

  public Transaction[] getTransactions() {
    Transaction[] copy = new Transaction[transactions.length];
    System.arraycopy(transactions, 0, copy, 0, transactions.length);
    return copy;
  }

  public static class Transaction {
    public final int rowIndex;
    public final long timestamp;
    public final BigDecimal balance;
    public final BigDecimal available;
    public final BigDecimal balanceDelta;
    public final BigDecimal availableDelta;
    public final String currency;
    public final String description;

    public Transaction(
        @JsonProperty(value = "row_index", required = true) int rowIndex,
        @JsonProperty(value = "timestamp", required = true) long timestamp,
        @JsonProperty(value = "balance", required = true) BigDecimal balance,
        @JsonProperty(value = "available", required = true) BigDecimal available,
        @JsonProperty(value = "balance_delta", required = true) BigDecimal balanceDelta,
        @JsonProperty(value = "available_delta", required = true) BigDecimal availableDelta,
        @JsonProperty(value = "currency", required = true) String currency,
        @JsonProperty(value = "description", required = true) String description) {
      this.rowIndex = rowIndex;
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
      return "Transaction [rowIndex="
          + rowIndex
          + ", timestamp="
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
