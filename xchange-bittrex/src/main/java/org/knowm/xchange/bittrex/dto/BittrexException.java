package org.knowm.xchange.bittrex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/** @author walec51 */
public class BittrexException extends HttpStatusExceptionSupport {

  @JsonProperty private String code;

  @JsonProperty private String details;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
