package org.knowm.xchange.coinsph.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CoinsphOrderType {
  LIMIT("LIMIT"),
  MARKET("MARKET"),
  STOP_LOSS("STOP_LOSS"),
  STOP_LOSS_LIMIT("STOP_LOSS_LIMIT"),
  TAKE_PROFIT("TAKE_PROFIT"),
  TAKE_PROFIT_LIMIT("TAKE_PROFIT_LIMIT"),
  LIMIT_MAKER("LIMIT_MAKER"); // Coins.ph docs mention this, similar to postOnly

  private final String value;

  CoinsphOrderType(String value) {
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
