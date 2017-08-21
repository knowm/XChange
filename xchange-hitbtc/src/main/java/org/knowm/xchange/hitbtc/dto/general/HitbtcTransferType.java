package org.knowm.xchange.hitbtc.dto.general;

public enum HitbtcTransferType {

  BANK_TO_EXCHANGE("bankToExchange"),
  EXCHANGE_TO_BANK("exchangeToBank");

  private final String type;

  HitbtcTransferType(String type) {

    this.type = type;
  }

  @Override
  public String toString() {

    return type;
  }

}
