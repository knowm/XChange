package org.knowm.xchange.dsx.dto;

public enum DsxTransferType {
  BANK_TO_EXCHANGE("bankToExchange"),
  EXCHANGE_TO_BANK("exchangeToBank");

  private final String type;

  DsxTransferType(String type) {

    this.type = type;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "DsxTransferType{" + "type='" + type + '\'' + '}';
  }
}
