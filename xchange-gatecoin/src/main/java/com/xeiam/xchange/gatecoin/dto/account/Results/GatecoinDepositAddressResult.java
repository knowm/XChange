package com.xeiam.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.GatecoinResult;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import com.xeiam.xchange.gatecoin.dto.marketdata.ResponseStatus;

/**
 * @author sumedha
 */
public class GatecoinDepositAddressResult extends GatecoinResult {
  private final GatecoinDepositAddress[] addresses;

  public GatecoinDepositAddressResult(
      @JsonProperty("addresses") GatecoinDepositAddress[] addresses,
      @JsonProperty("responseStatus") ResponseStatus responseStatus
  ) {
    super(responseStatus);
    this.addresses = addresses;
  }

  public GatecoinDepositAddress[] getAddresses() {
    return addresses;
  }
}
