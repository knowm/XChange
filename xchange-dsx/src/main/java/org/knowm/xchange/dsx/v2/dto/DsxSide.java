package org.knowm.xchange.dsx.v2.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DsxSide {
  BUY("buy"),
  SELL("sell");

  private final String value;

  DsxSide(String value) {

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
