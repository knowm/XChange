package org.knowm.xchange.dsx.dto.account;

/**
 * @author Mikhail Wall
 */

public class DSXCryptoDepositAddress {

  private final String cryptoAddress;
  private final Address newAddress;
  private final String currency;

  public DSXCryptoDepositAddress(String cryptoAddress, Address newAddress, String currency) {
    this.cryptoAddress = cryptoAddress;
    this.newAddress = newAddress;
    this.currency = currency;
  }

  public String getCryptoAddress() {
    return cryptoAddress;
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
        "cryptoAddress='" + cryptoAddress + '\'' +
        ", newAddress=" + newAddress +
        ", currency='" + currency + '\'' +
        '}';
  }
}
