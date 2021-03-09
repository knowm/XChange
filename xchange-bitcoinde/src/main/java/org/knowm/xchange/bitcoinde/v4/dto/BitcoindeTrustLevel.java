package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BitcoindeTrustLevel {
  BRONZE("bronze"),
  SILVER("silver"),
  GOLD("gold"),
  PLATIN("platin");

  private final String value;

  BitcoindeTrustLevel(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
