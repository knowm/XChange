package org.knowm.xchange.binance.dto.account.margin;

public enum TransferType {
  TO_MARGIN(1),
  FROM_MARGIN(2);

  private final int code;

  TransferType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
