package org.knowm.xchange.ascendex;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class AscendexException extends HttpStatusExceptionSupport {

  private final int code;

  public AscendexException(
      @JsonProperty("code") int code, @JsonProperty("message") String message) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
