package com.xeiam.xchange.ripple.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "currency", "counterparty", "value" })
public final class RippleAmount {

  @JsonProperty("currency")
  private String currency;
  @JsonProperty("counterparty")
  private String counterparty;
  @JsonProperty("value")
  private String value;

  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(final String currency) {
    this.currency = currency;
  }

  @JsonProperty("counterparty")
  public String getCounterparty() {
    return counterparty;
  }

  @JsonProperty("counterparty")
  public void setCounterparty(final String counterparty) {
    this.counterparty = counterparty;
  }

  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  @JsonProperty("value")
  public void setValue(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("Amount [currency=%s, counterparty=%s, value=%s]", //
        currency, counterparty, value);
  }
}