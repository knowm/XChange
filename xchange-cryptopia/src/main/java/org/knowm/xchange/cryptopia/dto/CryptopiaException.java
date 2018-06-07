package org.knowm.xchange.cryptopia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/** @author walec51 */
public class CryptopiaException extends HttpStatusExceptionSupport {

  @JsonProperty("Error")
  private String error;

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public String getMessage() {
    return error;
  }
}
