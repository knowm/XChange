package org.knowm.xchange.poloniex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.ExceptionalReturnContentException;

/** Created by John on 03/12/2017. */
public class TransferResponse {

  private int success;
  private String message;

  public TransferResponse(
      @JsonProperty("success") int success, @JsonProperty("message") String message) {
    if (success != 1) {
      throw new ExceptionalReturnContentException("Error returned: " + success);
    }
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
