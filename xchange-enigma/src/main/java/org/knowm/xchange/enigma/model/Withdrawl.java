package org.knowm.xchange.enigma.model;

public enum Withdrawl {
  CRYPTO_SETTLEMENT(1),
  CASH(2);

  private int value;

  private Withdrawl(int value) {
    this.value = value;
  }

  /** @return the value */
  public int getValue() {
    return value;
  }
}
