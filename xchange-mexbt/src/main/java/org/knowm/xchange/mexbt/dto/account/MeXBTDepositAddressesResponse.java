package org.knowm.xchange.mexbt.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;

public class MeXBTDepositAddressesResponse extends MeXBTResponse {

  private final MeXBTDepositAddress[] addresses;

  public MeXBTDepositAddressesResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason,
      @JsonProperty("addresses") MeXBTDepositAddress[] addresses) {
    super(isAccepted, rejectReason);
    this.addresses = addresses;
  }

  public MeXBTDepositAddress[] getAddresses() {
    return addresses;
  }

}
