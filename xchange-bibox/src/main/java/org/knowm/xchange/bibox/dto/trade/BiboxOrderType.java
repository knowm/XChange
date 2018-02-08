package org.knowm.xchange.bibox.dto.trade;

/**
 * @author odrotleff
 */
public enum BiboxOrderType {
  MARKET_ORDER(1),
  LIMIT_ORDER(2);

  private int orderType;

  private BiboxOrderType(int orderType) {
    this.orderType = orderType;
  }

  public int asInt() {
    return orderType;
  }
}
