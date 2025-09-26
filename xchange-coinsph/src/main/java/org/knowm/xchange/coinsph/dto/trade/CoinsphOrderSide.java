package org.knowm.xchange.coinsph.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CoinsphOrderSide {
  BUY("BUY"),
  SELL("SELL");

  private final String value;

  CoinsphOrderSide(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getValue();
  }
}
