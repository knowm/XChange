package org.knowm.xchange.mexc.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.mexc.MEXCErrorUtils;
import si.mazi.rescu.HttpStatusExceptionSupport;

import static org.knowm.xchange.mexc.MEXCErrorUtils.*;

public class MEXCException extends HttpStatusExceptionSupport {

  private final String message;
  private final int code;

  public MEXCException(@JsonProperty("msg") String message, @JsonProperty("code") int code) {
    super(message);
    this.message = message;
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return code + "[" + getOptionalErrorMessage(code).orElse("null") + "]:" + message;
  }
}
