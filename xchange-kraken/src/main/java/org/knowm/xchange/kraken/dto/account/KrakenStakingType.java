package org.knowm.xchange.kraken.dto.account;

public enum KrakenStakingType {
  BONDING("bonding"),
  REWARD("reward"),
  UNBONDING("unbonding");

  private final String code;

  KrakenStakingType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
