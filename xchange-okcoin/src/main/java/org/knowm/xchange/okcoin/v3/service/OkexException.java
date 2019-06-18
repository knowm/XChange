package org.knowm.xchange.okcoin.v3.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import si.mazi.rescu.HttpStatusExceptionSupport;

@SuppressWarnings("serial")
@Setter
public class OkexException extends HttpStatusExceptionSupport {

  // {"code":30015,"message":"Invalid OK_ACCESS_PASSPHRASE"}

  @Getter
  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @Override
  public String getMessage() {
    return String.format("[%s] %s", code, message);
  }
}
