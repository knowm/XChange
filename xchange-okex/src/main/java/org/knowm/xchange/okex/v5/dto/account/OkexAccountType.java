package org.knowm.xchange.okex.v5.dto.account;

public enum OkexAccountType {
  FUNDING_ACCOUNT("6"),
  TRADING_ACCOUNT("18");

  private final String value;

  OkexAccountType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
