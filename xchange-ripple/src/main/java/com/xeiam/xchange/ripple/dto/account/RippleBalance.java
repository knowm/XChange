package com.xeiam.xchange.ripple.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "value", "currency", "counterparty" })
public final class RippleBalance {

  @JsonProperty("value")
  private BigDecimal value;
  @JsonProperty("currency")
  private String currency;
  @JsonProperty("counterparty")
  private String counterparty;

  @JsonProperty("value")
  public BigDecimal getValue() {
    return value;
  }

  @JsonProperty("value")
  public void setValue(final BigDecimal value) {
    this.value = value;
  }

  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(final String value) {
    currency = value;
  }

  @JsonProperty("counterparty")
  public String getCounterparty() {
    return counterparty;
  }

  @JsonProperty("counterparty")
  public void setCounterparty(final String value) {
    counterparty = value;
  }

  @Override
  public String toString() {
    return String.format("Balance [value=%s, currency=%s, counterparty=%s]", //
        currency, counterparty, value);
  }
}