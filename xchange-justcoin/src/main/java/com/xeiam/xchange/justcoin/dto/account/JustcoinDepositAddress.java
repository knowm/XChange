package com.xeiam.xchange.justcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class JustcoinDepositAddress {

  private final String address;

  /**
   * Constructor
   * 
   * @param address
   */
  public JustcoinDepositAddress(final @JsonProperty("address") String address) {

    this.address = address;
  }

  public String getAddress() {

    return address;
  }

  @Override
  public String toString() {

    return "JustcoinDepositAddress [address=" + address + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JustcoinDepositAddress other = (JustcoinDepositAddress) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    }
    else if (!address.equals(other.address)) {
      return false;
    }
    return true;
  }
}
