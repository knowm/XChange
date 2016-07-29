package org.knowm.xchange.mexbt.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;

public class MeXBTBalanceResponse extends MeXBTResponse {

  private final MeXBTBalance[] currencies;

  public MeXBTBalanceResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason,
      @JsonProperty("currencies") MeXBTBalance[] currencies) {
    super(isAccepted, rejectReason);
    this.currencies = currencies;
  }

  public MeXBTBalance[] getCurrencies() {
    return currencies;
  }

}
