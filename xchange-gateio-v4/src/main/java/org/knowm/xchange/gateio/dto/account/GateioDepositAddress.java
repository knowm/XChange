package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;

public class GateioDepositAddress extends GateioBaseResponse {

  private final String baseAddress;

  private final String addressTag;

  /**
   * Constructor
   *
   * @param theAvailable
   * @param theLocked
   */
  public GateioDepositAddress(
      @JsonProperty("addr") String addr,
      @JsonProperty("result") boolean result,
      @JsonProperty("message") final String message) {

    super(result, message);

    String[] addressComponent = addr.split("/");
    this.baseAddress = addressComponent[0];
    if (addressComponent.length > 1) {
      this.addressTag = addressComponent[1];
    } else {
      this.addressTag = null;
    }
  }

  public String getBaseAddress() {
    return baseAddress;
  }

  public String getAddressTag() {
    return addressTag;
  }

  @Override
  public String toString() {

    return "BTERDepositAddressReturn [baseAddress="
        + baseAddress
        + ", addressTag="
        + addressTag
        + "]";
  }
}
