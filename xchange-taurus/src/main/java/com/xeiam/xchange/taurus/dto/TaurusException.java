package com.xeiam.xchange.taurus.dto;

import si.mazi.rescu.HttpStatusExceptionSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaurusException extends HttpStatusExceptionSupport {
  public TaurusException(@JsonProperty("error") Object error) {
    super(error.toString());
  }
}
