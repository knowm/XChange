package org.knowm.xchange.anx.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Data object representing a Value from ANX */
public final class ANXValue {

  private final BigDecimal value;
  private final String currency;

  /**
   * Constructor
   *
   * @param value
   * @param currency
   */
  public ANXValue(
      @JsonProperty("value") BigDecimal value, @JsonProperty("currency") String currency) {

    this.value = value;
    this.currency = currency;
  }

  public BigDecimal getValue() {

    return value;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    return "ANXValue [value=" + value + ", currency=" + currency + "]";
  }
}
