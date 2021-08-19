package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BitcoindeType {
  BUY("buy"),
  SELL("sell");

  private final String value;

  BitcoindeType(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
