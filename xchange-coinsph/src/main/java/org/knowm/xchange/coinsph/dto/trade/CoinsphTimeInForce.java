package org.knowm.xchange.coinsph.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import org.knowm.xchange.dto.Order;

public enum CoinsphTimeInForce implements Order.IOrderFlags {
  GTC("GTC"), // Good Til Canceled
  IOC("IOC"), // Immediate Or Cancel
  FOK("FOK"); // Fill Or Kill

  private final String value;

  CoinsphTimeInForce(String value) {
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
