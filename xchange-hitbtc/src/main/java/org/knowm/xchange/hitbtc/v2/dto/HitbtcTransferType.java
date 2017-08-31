package org.knowm.xchange.hitbtc.v2.dto;

public enum HitbtcTransferType {

  BANK_TO_EXCHANGE("bankToExchange"),
  EXCHANGE_TO_BANK("exchangeToBank");

  private final String type;

  HitbtcTransferType(String type) {

    this.type = type;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "HitbtcTransferType{" +
        "type='" + type + '\'' +
        '}';
  }
}
