package org.knowm.xchange.livecoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/** @author walec51 */
public class LivecoinException extends HttpStatusExceptionSupport {

  @JsonProperty("errorCode")
  private int errorCode;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }
}
