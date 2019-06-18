package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import org.knowm.xchange.okcoin.v3.service.OkexException;

@Setter
public abstract class OkexResponse {

  /** result of the order. Error message will be returned if the order failed. */
  private boolean result;

  /** Error code for order placement. Success = 0. Failure = error code */
  @JsonProperty("error_code")
  private String errorCode;

  /**
   * It will be blank if order placement is success. Error message will be returned if order
   * placement fails.
   */
  @JsonProperty("error_message")
  private String errorMessage;

  public void checkResult() {
    if (!result) {
      OkexException e = new OkexException();
      e.setCode(errorCode);
      e.setMessage(errorMessage);
      throw e;
    }
  }
}
