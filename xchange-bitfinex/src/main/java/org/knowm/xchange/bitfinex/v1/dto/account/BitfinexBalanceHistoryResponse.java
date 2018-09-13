package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexBalanceHistoryResponse {

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("description")
  private String description;

  @JsonProperty("timestamp")
  private BigDecimal timestamp;

  public BitfinexBalanceHistoryResponse(
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("description") String description,
      @JsonProperty("timestamp") BigDecimal timestamp) {

    this.currency = currency;
    this.balance = balance;
    this.amount = amount;
    this.description = description;
    this.timestamp = timestamp;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {

    final StringBuilder builder = new StringBuilder();
    builder.append("BitfinexPastTransactionsResponse [currency=");
    builder.append(currency);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", balance=");
    builder.append(balance);
    builder.append(", description=");
    builder.append(description);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append("]");
    return builder.toString();
  }
}
