package org.knowm.xchange.hitbtc.v2.dto;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcBalance {

  private final String currency;
  private final BigDecimal available;
  private final BigDecimal reserved;

  public HitbtcBalance(@JsonProperty("currency") String currency, @JsonProperty("available") BigDecimal available,
      @JsonProperty("reserved") BigDecimal reserved) {

    this.currency = currency;
    this.available = available;
    this.reserved = reserved;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getReserved() {

    return reserved;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this)
        .append("currency", currency)
        .append("available", available)
        .append("reserved", reserved)
        .toString();
  }
}
