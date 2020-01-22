package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CryptowatchPriceChange {

  private final BigDecimal percentage;
  private final BigDecimal absolute;

  public CryptowatchPriceChange(
      @JsonProperty("percentage") BigDecimal percentage,
      @JsonProperty("absolute") BigDecimal absolute) {
    this.percentage = percentage;
    this.absolute = absolute;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public BigDecimal getAbsolute() {
    return absolute;
  }

  @Override
  public String toString() {
    return "CryptowatchPriceChange{" + "percentage=" + percentage + ", absolute=" + absolute + '}';
  }
}
