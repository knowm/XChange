package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BitcoindeOrderState {
  EXPIRED(-2),
  CANCELLED(-1),
  PENDING(0);

  private final int value;

  BitcoindeOrderState(final int value) {
    this.value = value;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static BitcoindeOrderState fromValue(final int value) {
    for (BitcoindeOrderState bitcoindeOrderState : BitcoindeOrderState.values()) {
      if (value == bitcoindeOrderState.getValue()) {
        return bitcoindeOrderState;
      }
    }

    throw new IllegalArgumentException("Unknown OrderState value: " + value);
  }
}
