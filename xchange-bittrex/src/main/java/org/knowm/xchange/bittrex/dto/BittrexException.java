package org.knowm.xchange.bittrex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/** @author walec51 */
public class BittrexException extends HttpStatusExceptionSupport {

  @JsonProperty("message")
  private String message;

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
