package com.xeiam.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexBalancesResponse {

  private final String type;
  private final String currency;
  private final BigDecimal amount;

  public BitfinexBalancesResponse(@JsonProperty("type") String type, @JsonProperty("currency") String currency, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("available") BigDecimal available) {

    this.type = type;
    this.currency = currency;
    this.amount = amount;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getCurrency() {

    return currency;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexBalancesResponse [type=");
    builder.append(type);
    builder.append(", currency=");
    builder.append(currency);
    builder.append(", amount=");
    builder.append(amount);
    builder.append("]");
    return builder.toString();
  }
}
