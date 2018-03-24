package org.knowm.xchange.gatecoin.dto.account.Results;

import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sumedha
 */
public class GatecoinDepositAddressResult extends GatecoinResult {
  private final GatecoinDepositAddress[] addresses;

  public GatecoinDepositAddressResult(@JsonProperty("addresses") GatecoinDepositAddress[] addresses,
      @JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
    this.addresses = addresses;
  }

  public GatecoinDepositAddress[] getAddresses() {
    return addresses;
  }
}
