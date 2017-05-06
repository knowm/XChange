package org.knowm.xchange.gdax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusExceptionSupport;

@SuppressWarnings("serial")
public class GDAXException extends HttpStatusExceptionSupport {
  public GDAXException(@JsonProperty("message") String reason) {
    super(reason);
  }
}
