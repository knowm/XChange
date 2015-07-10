package com.xeiam.xchange.mexbt.dto;

import si.mazi.rescu.ExceptionalReturnContentException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTResponse {

  public MeXBTResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason) {
    if (!isAccepted) {
      throw new ExceptionalReturnContentException(rejectReason);
    }
  }

}
