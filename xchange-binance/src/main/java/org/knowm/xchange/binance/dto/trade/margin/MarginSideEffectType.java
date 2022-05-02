package org.knowm.xchange.binance.dto.trade.margin;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.dto.Order;

public enum MarginSideEffectType implements Order.IOrderFlags {
  NO_SIDE_EFFECT,
  MARGIN_BUY,
  AUTO_REPAY;

  @JsonCreator
  public static MarginSideEffectType getSideEffectType(String s) {
    try {
      return MarginSideEffectType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown side effect type " + s + ".");
    }
  }
}
