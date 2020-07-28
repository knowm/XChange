package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BitcoindeAccountLedgerType {
  ALL("all"),
  BUY("buy"),
  SELL("sell"),
  INPAYMENT("inpayment"),
  PAYOUT("payout"),
  AFFILIATE("affiliate"),
  WELCOME_BTC("welcome_btc"),
  BUY_YUBIKEY("buy_yubikey"),
  BUY_GOLDSHOP("buy_goldshop"),
  BUY_DIAMONDSHOP("buy_diamondshop"),
  KICKBACK("kickback"),
  OUTGOING_FEE_VOLUNTARY("outgoing_fee_voluntary");

  private final String value;

  BitcoindeAccountLedgerType(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
