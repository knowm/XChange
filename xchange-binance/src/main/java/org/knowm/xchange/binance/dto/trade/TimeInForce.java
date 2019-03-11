package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.dto.Order.IOrderFlags;

public enum TimeInForce implements IOrderFlags {
  GTC,
  FOK,
  IOC;

  @JsonCreator
  public static TimeInForce getTimeInForce(String s) {
    try {
      return TimeInForce.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown ordtime in force " + s + ".");
    }
  }
}
