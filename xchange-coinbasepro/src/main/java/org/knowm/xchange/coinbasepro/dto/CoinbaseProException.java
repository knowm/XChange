package org.knowm.xchange.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class CoinbaseProException extends HttpStatusExceptionSupport {

  public CoinbaseProException(@JsonProperty("message") String reason) {
    super(reason);
  }
}
