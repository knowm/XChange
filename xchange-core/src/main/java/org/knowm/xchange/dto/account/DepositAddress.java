package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.util.Objects;

public final class DepositAddress implements Serializable {

  private final String address;

  private final String destinationTag;

  public DepositAddress(String address, String destinationTag) {
    this.address = address;
    this.destinationTag = destinationTag;
  }

  public String getAddress() {
    return address;
  }

  public String getDestinationTag() {
    return destinationTag;
  }

  @Override
  public String toString() {
    return "DepositAddress{"
        + "address='"
        + address
        + '\''
        + ", destinationTag='"
        + destinationTag
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DepositAddress that = (DepositAddress) o;
    return Objects.equals(address, that.address)
        && Objects.equals(destinationTag, that.destinationTag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, destinationTag);
  }
}
