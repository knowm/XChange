package org.knowm.xchange.poloniex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.ExceptionalReturnContentException;

public class WithdrawalResponse {
  private String response;

  public WithdrawalResponse(
      @JsonProperty("response") String response, @JsonProperty("error") String error) {
    if (error != null) {
      throw new ExceptionalReturnContentException("Error returned: " + error);
    }
    this.response = response;
  }

  public String getResponse() {
    return response;
  }
}
