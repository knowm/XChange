package org.knowm.xchange.luno.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoFundingAddress {

  public final String asset;
  public final String address;
  public final BigDecimal totalReceived;
  public final BigDecimal totalUnconfirmed;

  public LunoFundingAddress(@JsonProperty(value = "asset", required = true) String asset
      , @JsonProperty(value = "address", required = true) String address
      , @JsonProperty(value = "total_received", required = true) BigDecimal totalReceived
      , @JsonProperty(value = "total_unconfirmed", required = true) BigDecimal totalUnconfirmed) {
    this.asset = asset;
    this.address = address;
    this.totalReceived = totalReceived;
    this.totalUnconfirmed = totalUnconfirmed;
  }

  @Override
  public String toString() {
    return "LunoFundingAddress [asset=" + asset + ", address=" + address + ", totalReceived=" + totalReceived
        + ", totalUnconfirmed=" + totalUnconfirmed + "]";
  }

}
