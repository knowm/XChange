package org.knowm.xchange.independentreserve.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IndependentReserveDepositAddressResponse {
  private String depositAddress;
  private String lastCheckedTimestampUtc;
  private String nextUpdateTimestampUtc;

  @JsonCreator
  public IndependentReserveDepositAddressResponse(
      @JsonProperty("DepositAddress") String depositAddress,
      @JsonProperty("LastCheckedTimestampUtc") String lastCheckedTimestampUtc,
      @JsonProperty("NextUpdateTimestampUtc") String nextUpdateTimestampUtc) {
    this.depositAddress = depositAddress;
    this.lastCheckedTimestampUtc = lastCheckedTimestampUtc;
    this.nextUpdateTimestampUtc = nextUpdateTimestampUtc;
  }

  public String getDepositAddress() {
    return depositAddress;
  }

  public String getLastCheckedTimestampUtc() {
    return lastCheckedTimestampUtc;
  }

  public String getNextUpdateTimestampUtc() {
    return nextUpdateTimestampUtc;
  }
}
