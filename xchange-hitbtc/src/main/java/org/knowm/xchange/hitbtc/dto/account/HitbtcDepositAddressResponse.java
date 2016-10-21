package org.knowm.xchange.hitbtc.dto.account;

import org.knowm.xchange.hitbtc.dto.HitbtcBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcDepositAddressResponse extends HitbtcBaseResponse {

  private final String address;

  public HitbtcDepositAddressResponse(@JsonProperty("address") String address) {

    this.address = address;
  }

  public String getAddress() {

    return address;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcDepositAddressResponse [address=");
    builder.append(address);
    builder.append("]");
    return builder.toString();
  }
}
