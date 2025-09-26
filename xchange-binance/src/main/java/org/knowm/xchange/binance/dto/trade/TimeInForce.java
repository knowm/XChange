package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.knowm.xchange.dto.Order.IOrderFlags;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public enum TimeInForce implements IOrderFlags {

  GTC, // Good Till Cancel
  GTX, // Good Till Crossing (Post Only)
  FOK, // Fill or Kill
  IOC, // Immediate or Cancel
  GTE_GTC, // Good Till Expired or Canceled
  DAY, // Day Order
  GTD, // Good Till Date
  ;

  @JsonCreator
  public static TimeInForce getTimeInForce(String s) {
    try {
      return TimeInForce.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown ordtime in force " + s + ".");
    }
  }
}
