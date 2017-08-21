package org.knowm.xchange.hitbtc.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcBalance {

  private final String currencyCode;
  private final BigDecimal cash;
  private final BigDecimal reserved;

  public HitbtcBalance(@JsonProperty("currency_code") String currencyCode, @JsonProperty("cash") BigDecimal cash,
      @JsonProperty("reserved") BigDecimal reserved) {

    this.currencyCode = currencyCode;
    this.cash = cash;
    this.reserved = reserved;
  }

  public String getCurrencyCode() {

    return currencyCode;
  }

  public BigDecimal getCash() {

    return cash;
  }

  public BigDecimal getReserved() {

    return reserved;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcBalance [currencyCode=");
    builder.append(currencyCode);
    builder.append(", cash=");
    builder.append(cash);
    builder.append(", reserved=");
    builder.append(reserved);
    builder.append("]");
    return builder.toString();
  }
}
