package org.knowm.xchange.gdax.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GDAXAccount {
  private final String id;
  private final String currency;
  private final String profile_id;
  private final BigDecimal balance;
  private final BigDecimal hold;
  private final BigDecimal available;

  public GDAXAccount(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("profile_id") String profile_id,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("hold") BigDecimal hold,
      @JsonProperty("available") BigDecimal available) {
    this.id = id;
    this.currency = currency;
    this.profile_id = profile_id;
    this.balance = balance;
    this.hold = hold;
    this.available = available;
  }

  public String getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public String getProfile_id() {
    return profile_id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getHold() {
    return hold;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExAccount [id=");
    builder.append(id);
    builder.append(", currency=");
    builder.append(currency);
    builder.append(", profile_id=");
    builder.append(profile_id);
    builder.append(", balance=");
    builder.append(balance);
    builder.append(", hold=");
    builder.append(hold);
    builder.append(", available=");
    builder.append(available);
    builder.append("]");
    return builder.toString();
  }
}
