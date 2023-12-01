package org.knowm.xchange.enigma.model;

public enum Side {
  BUY(1),
  SELL(2);

  private int value;

  private Side(int value) {
    this.value = value;
  }

  /**
   * @return the value
   */
  public int getValue() {
    return value;
  }
}
