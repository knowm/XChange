package org.knowm.xchange.okex.v5;

public enum OkexDepositState {
  WAITING_FOR_CONFORMATION("0"),
  DEPOSIT_CREATED("1"),
  DEPOSIT_SUCCESS("2"),
  PENDING("8"),
  FROZEN("12"),
  INTERCEPTED("13");

  private final String state;

  OkexDepositState(final String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }
}
