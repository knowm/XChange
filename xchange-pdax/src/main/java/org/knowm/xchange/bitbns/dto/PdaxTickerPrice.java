package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;

public class PdaxTickerPrice {

  private String currency;
  private String display;
  private String display_short;
  private BigDecimal value;
  private long value_int;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDisplay() {
    return display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public String getDisplay_short() {
    return display_short;
  }

  public void setDisplay_short(String display_short) {
    this.display_short = display_short;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public long getValue_int() {
    return value_int;
  }

  public void setValue_int(long value_int) {
    this.value_int = value_int;
  }

  @Override
  public String toString() {
    return "PdaxTickerPrice [currency="
        + currency
        + ", display="
        + display
        + ", display_short="
        + display_short
        + ", value="
        + value
        + ", value_int="
        + value_int
        + "]";
  }
}
