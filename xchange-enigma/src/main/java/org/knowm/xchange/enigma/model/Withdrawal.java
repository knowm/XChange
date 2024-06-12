package org.knowm.xchange.enigma.model;

public enum Withdrawal {
  CRYPTO_SETTLEMENT(1),
  CASH(2);

  private int value;

  private Withdrawal(int value) {
    this.value = value;
  }

  /**
   * @return the value
   */
  public int getValue() {
    return value;
  }
}
