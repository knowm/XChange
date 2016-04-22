package org.knowm.xchange.mexbt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.ExceptionalReturnContentException;

public class MeXBTResponse {

  public MeXBTResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason) {
    if (!isAccepted) {
      throw new ExceptionalReturnContentException(rejectReason);
    }
  }

}
