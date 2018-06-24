package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets IdexBuySell */
public enum IdexBuySell {
  BUY("buy"),

  SELL("sell");

  private final String value;

  IdexBuySell(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static IdexBuySell fromValue(String text) {
    for (IdexBuySell b : IdexBuySell.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
