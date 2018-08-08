package org.knowm.xchange.upbit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class UpbitBalance {
  private final String currency;
  private final BigDecimal balance;
  private final BigDecimal locked;
  private final BigDecimal avg_krw_buy_price;
  private final boolean modified;

  public UpbitBalance(
      @JsonProperty("currency") String currency,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("locked") BigDecimal locked,
      @JsonProperty("avg_krw_buy_price") BigDecimal avg_krw_buy_price,
      @JsonProperty("modified") boolean modified) {
    this.currency = currency;
    this.balance = balance;
    this.locked = locked;
    this.avg_krw_buy_price = avg_krw_buy_price;
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

  public BigDecimal getAvg_krw_buy_price() {
    return avg_krw_buy_price;
  }

  public boolean isModified() {
    return modified;
  }
}
