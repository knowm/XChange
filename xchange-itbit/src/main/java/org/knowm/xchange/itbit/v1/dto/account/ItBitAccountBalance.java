package org.knowm.xchange.itbit.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ItBitAccountBalance {

  private final BigDecimal availableBalance;
  private final BigDecimal totalBalance;
  private final String currency;

  public ItBitAccountBalance(
      @JsonProperty("availableBalance") BigDecimal availableBalance,
      @JsonProperty("totalBalance") BigDecimal totalBalance,
      @JsonProperty("currency") String currency) {

    this.availableBalance = availableBalance;
    this.totalBalance = totalBalance;
    this.currency = currency;
  }

  public BigDecimal getAvailableBalance() {

    return availableBalance;
  }

  public BigDecimal getTotalBalance() {

    return totalBalance;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitAccountBalance [availableBalance=");
    builder.append(availableBalance);
    builder.append(", totalBalance=");
    builder.append(totalBalance);
    builder.append(", currency=");
    builder.append(currency);
    builder.append("]");
    return builder.toString();
  }
}
