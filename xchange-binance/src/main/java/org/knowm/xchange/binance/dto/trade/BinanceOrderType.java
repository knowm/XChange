package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BinanceOrderType {
  LIMIT,
  MARKET,
  TAKE_PROFIT_LIMIT,
  STOP_LOSS_LIMIT,
  STOP_LOSS,
  TAKE_PROFIT,
  LIMIT_MAKER;

  @JsonCreator
  public static BinanceOrderType getOrderType(String s) {
    try {
      return BinanceOrderType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown order type " + s + ".");
    }
  }
}
