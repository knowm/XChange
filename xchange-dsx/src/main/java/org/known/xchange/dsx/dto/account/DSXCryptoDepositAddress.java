package org.known.xchange.dsx.dto.account;

/**
 * @author Mikhail Wall
 */

public class DSXCryptoDepositAddress {

  private final String cryproAddress;
  private final Address newAddress;
  private final String currency;

  public DSXCryptoDepositAddress(String cryproAddress, Address newAddress, String currency) {
    this.cryproAddress = cryproAddress;
    this.newAddress = newAddress;
    this.currency = currency;
  }

  public String getCryproAddress() {
    return cryproAddress;
  }

  public Address getNewAddress() {
    return newAddress;
  }

  public String getCurrency() {
    return currency;
  }

  public enum Address {
    old_address, new_address
  }

  @Override
  public String toString() {
    return "DSXCryptoDepositAddress{" +
        "cryproAddress='" + cryproAddress + '\'' +
        ", newAddress=" + newAddress +
        ", currency='" + currency + '\'' +
        '}';
  }
}
