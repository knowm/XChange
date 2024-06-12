package org.knowm.xchange.btcturk.dto;

/**
 * @author mertguner
 */
public enum BTCTurkOrderMethods {
  LIMIT(0),
  MARKET(1),
  STOP_LIMIT(2),
  STOP_MARKET(3);

  private final int orderMethod;

  BTCTurkOrderMethods(int orderMethod) {
    this.orderMethod = orderMethod;
  }

  public int getValue() {
    return orderMethod;
  }

  @Override
  public String toString() {

    switch (orderMethod) {
      case 0:
        return "Limit";
      case 1:
        return "Market";
      case 2:
        return "Stop Limit";
      case 3:
        return "Stop Market";
      default:
        return "";
    }
  }
}
