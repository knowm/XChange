package com.xeiam.xchange.ripple.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonPropertyOrder({ "currency", "counterparty", "value" })
public final class RippleAmount {

  @JsonProperty("currency")
  private String currency;
  @JsonProperty("counterparty")
  private String counterparty;
  @JsonProperty("value")
  private BigDecimal value;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(final String currency) {
    this.currency = currency;
  }

  public String getCounterparty() {
    return counterparty;
  }

  public void setCounterparty(final String counterparty) {
    this.counterparty = counterparty;
  }

  @JsonSerialize(using = ToStringSerializer.class)
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(final BigDecimal value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("%s [currency=%s, counterparty=%s, value=%s]", getClass().getSimpleName(), currency, counterparty, value);
  }
}