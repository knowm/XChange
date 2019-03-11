package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PoloniexGenerateNewAddressResponse {

  private boolean success;

  private String address;

  @JsonCreator
  public PoloniexGenerateNewAddressResponse(
      @JsonProperty("success") Boolean success, @JsonProperty("response") String address) {
    this.success = success;
    this.address = address;
  }

  public boolean success() {
    return success;
  }

  public String getAddress() {
    return address;
  }
}
