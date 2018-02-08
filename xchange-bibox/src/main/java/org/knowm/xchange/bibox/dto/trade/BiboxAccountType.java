package org.knowm.xchange.bibox.dto.trade;

/**
 * @author odrotleff
 */
public enum BiboxAccountType {
  REGULAR(0),
  MARGIN(1);
  
  private int accountType;

  private BiboxAccountType(int accountType) {
    this.accountType = accountType;
  }

  public int asInt() {
    return accountType;
  }
}
