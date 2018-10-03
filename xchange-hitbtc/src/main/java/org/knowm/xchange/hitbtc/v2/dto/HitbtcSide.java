package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HitbtcSide {
  BUY("buy"),
  SELL("sell");

  private final String value;

  HitbtcSide(String value) {

    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  @JsonValue
  public String toString() {

    return value;
  }
}
