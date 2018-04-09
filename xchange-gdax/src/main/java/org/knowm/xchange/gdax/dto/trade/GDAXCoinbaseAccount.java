package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GDAXCoinbaseAccount {

  private final String name;
  private final boolean primaryAccount;
  private final String currency;
  private final boolean active;
  private final BigDecimal balance;
  private final String type;
  private final String id;

  public GDAXCoinbaseAccount(
      @JsonProperty("name") String name,
      @JsonProperty("primary") boolean primaryAccount,
      @JsonProperty("currency") String currency,
      @JsonProperty("active") boolean active,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("type") String type,
      @JsonProperty("id") String id) {
    this.name = name;
    this.primaryAccount = primaryAccount;
    this.currency = currency;
    this.active = active;
    this.balance = balance;
    this.type = type;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public boolean isPrimaryAccount() {
    return primaryAccount;
  }

  public String getCurrency() {
    return currency;
  }

  public boolean isActive() {
    return active;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }
}
