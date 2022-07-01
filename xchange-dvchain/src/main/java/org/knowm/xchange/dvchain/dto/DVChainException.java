package org.knowm.xchange.dvchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class DVChainException extends HttpStatusExceptionSupport {

  public DVChainException(@JsonProperty("message") String reason) {
    super(reason);
  }
}
