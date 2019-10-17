package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.util.Objects;

public final class AddressWithTag implements Serializable {

  private final String address;
  private final String addressTag;

  public AddressWithTag(String address, String addressTag) {
    this.address = address;
    this.addressTag = addressTag;
  }

  public String getAddress() {
    return address;
  }

  public String getAddressTag() {
    return addressTag;
  }

  @Override
  public String toString() {
    return "AddressWithTag{"
        + "address='"
        + address
        + '\''
        + ", addressTag='"
        + addressTag
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressWithTag that = (AddressWithTag) o;
    return Objects.equals(address, that.address) && Objects.equals(addressTag, that.addressTag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, addressTag);
  }
}
