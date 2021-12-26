package org.knowm.xchange.ftx;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class FtxException extends HttpStatusExceptionSupport {

  public FtxException(@JsonProperty("error") String message) {
    super(message);
  }
}
