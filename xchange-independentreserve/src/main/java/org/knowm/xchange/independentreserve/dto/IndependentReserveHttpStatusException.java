package org.knowm.xchange.independentreserve.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveHttpStatusException extends HttpStatusExceptionSupport {

  public IndependentReserveHttpStatusException(@JsonProperty("Message") String message) {
    super(message);
  }
}
