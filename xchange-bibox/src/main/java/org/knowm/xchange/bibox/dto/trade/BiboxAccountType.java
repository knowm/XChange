package org.knowm.xchange.bibox.dto.trade;

/** @author odrotleff */
public enum BiboxAccountType {
  REGULAR(0),
  MARGIN(1);

  private int accountType;

  private BiboxAccountType(int accountType) {
    this.accountType = accountType;
  }

  public static BiboxAccountType fromInt(int accountType) {
    switch (accountType) {
      case 0:
        return REGULAR;
      case 1:
        return MARGIN;
      default:
        throw new RuntimeException("Unexpected Bibox account type!");
    }
  }

  public int asInt() {
    return accountType;
  }
}
