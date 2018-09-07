package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BitstampRippleDepositAddress {

  @JsonProperty("address")
  private final String addressAndDt;

  @JsonIgnore private String address = null;

  @JsonIgnore private Long destinationTag = null;

  public BitstampRippleDepositAddress(@JsonProperty("address") String addressAndDt) {

    this.addressAndDt = addressAndDt;
    final String[] split = addressAndDt.split("\\?dt=");
    if (split.length == 2) {
      address = split[0];
      destinationTag = Long.parseLong(split[1]);
    }
  }

  public String getAddressAndDt() {

    return addressAndDt;
  }

  public String getAddress() {

    return address;
  }

  public Long getDestinationTag() {

    return destinationTag;
  }

  @Override
  public String toString() {

    return (address == null
        ? addressAndDt
        : String.format("RippleAddress[%s, dt=%s]", address, destinationTag));
  }
}
