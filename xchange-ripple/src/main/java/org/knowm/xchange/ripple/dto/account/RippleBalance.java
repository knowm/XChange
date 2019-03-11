package org.knowm.xchange.ripple.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

@JsonPropertyOrder({"value", "currency", "counterparty"})
public final class RippleBalance {

  @JsonProperty("value")
  private BigDecimal value;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("counterparty")
  private String counterparty;

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(final BigDecimal value) {
    this.value = value;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(final String value) {
    currency = value;
  }

  public String getCounterparty() {
    return counterparty;
  }

  public void setCounterparty(final String value) {
    counterparty = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [currency=%s, counterparty=%s, value=%s]",
        getClass().getSimpleName(), currency, counterparty, value);
  }
}
