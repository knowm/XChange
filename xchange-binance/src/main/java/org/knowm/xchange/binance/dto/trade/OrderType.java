package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.dto.Order.IOrderFlags;

public enum OrderType implements IOrderFlags {
  LIMIT,
  MARKET,
  TAKE_PROFIT_LIMIT,
  STOP_LOSS_LIMIT,
  STOP_LOSS,
  TAKE_PROFIT,
  LIMIT_MAKER;

  @JsonCreator
  public static OrderType getOrderType(String s) {
    try {
      return OrderType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown order type " + s + ".");
    }
  }
}
