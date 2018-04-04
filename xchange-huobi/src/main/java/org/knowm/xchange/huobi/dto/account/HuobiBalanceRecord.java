package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class HuobiBalanceRecord {

  private final BigDecimal balance;
  private final String currency;
  private final String type;

  public HuobiBalanceRecord(
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("currency") String currency,
      @JsonProperty("type") String type) {
    this.balance = balance;
    this.currency = currency;
    this.type = type;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getCurrency() {
    return currency;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return String.format("[balance = %s, currency = %s, type = %s]", balance, currency, type);
  }
}
