package org.knowm.xchange.cobinhood.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CobinhoodCoinBalance {
  private final String currency;
  private final BigDecimal totalAmount;
  private final BigDecimal onOrderAmount;

  public CobinhoodCoinBalance(
      @JsonProperty("currency") String currency,
      @JsonProperty("total") BigDecimal totalAmount,
      @JsonProperty("on_order") BigDecimal onOrderAmount) {
    this.currency = currency;
    this.totalAmount = totalAmount;
    this.onOrderAmount = onOrderAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public BigDecimal getOnOrderAmount() {
    return onOrderAmount;
  }

  @Override
  public String toString() {
    return "CobinhoodCoinBalance{"
        + "currency='"
        + currency
        + '\''
        + ", totalAmount="
        + totalAmount
        + ", onOrderAmount="
        + onOrderAmount
        + '}';
  }
}
