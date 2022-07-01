package org.knowm.xchange.enigma.model;

public enum ResponseException {
  ACCESS_DENIED(0),
  ORDER_ISSUE(5),
  NUMBER_FORMAT_INVALID(6),
  BOOLEAN_FORMAT_INVALID(61),
  USERNAME_ISSUE(100),
  PASSWORD_ISSUE(105),
  RFQ_ISSUE(55),
  RISK_LIMIT_ISSUE(56),
  GENERIC(500);

  private int code;

  private ResponseException(int code) {
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }
}
