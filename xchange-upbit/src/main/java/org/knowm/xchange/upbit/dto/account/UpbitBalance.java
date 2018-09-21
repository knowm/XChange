package org.knowm.xchange.upbit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class UpbitBalance {
  private final String currency;
  private final BigDecimal balance;
  private final BigDecimal locked;
  private final BigDecimal avgKrwBuyPrice;
  private final boolean modified;

  public UpbitBalance(
      @JsonProperty("currency") String currency,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("locked") BigDecimal locked,
      @JsonProperty("avg_krw_buy_price") BigDecimal avgKrwBuyPrice,
      @JsonProperty("modified") boolean modified) {
    this.currency = currency;
    this.balance = balance;
    this.locked = locked;
    this.avgKrwBuyPrice = avgKrwBuyPrice;
    this.modified = modified;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getLocked() {
    return locked;
  }

  public BigDecimal getAvgKrwBuyPrice() {
    return avgKrwBuyPrice;
  }

  public boolean isModified() {
    return modified;
  }
}
