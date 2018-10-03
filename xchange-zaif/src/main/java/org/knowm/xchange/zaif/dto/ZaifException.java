package org.knowm.xchange.zaif.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class ZaifException extends HttpStatusExceptionSupport {
  public ZaifException(@JsonProperty("error") String reason) {
    super(reason);
  }
}
