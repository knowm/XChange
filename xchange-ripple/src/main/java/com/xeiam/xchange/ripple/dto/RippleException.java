package com.xeiam.xchange.ripple.dto;

import si.mazi.rescu.HttpStatusExceptionSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class RippleException extends HttpStatusExceptionSupport {

  @JsonProperty("message")
  private String message;

  @JsonProperty("error")
  private String error;

  @JsonProperty("error_type")
  private String errorType;

  @Override
  public String getMessage() {
    return message;
  }

  public String getError() {
    return error;
  }

  public String getErrorType() {
    return errorType;
  }
}
