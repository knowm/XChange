package org.knowm.xchange.btcturk.dto;

/** @author mertguner */
public enum BTCTurkOrderTypes {
  Buy(0),
  Sell(1);

  private final int orderType;

  BTCTurkOrderTypes(int orderType) {
    this.orderType = orderType;
  }

  public int getValue() {
    return orderType;
  }

  @Override
  public String toString() {

    switch (orderType) {
      case 0:
        return "Buy";
      case 1:
        return "Sell";
      default:
        return "";
    }
  }
}
