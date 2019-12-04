package org.knowm.xchange.coindeal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import si.mazi.rescu.HttpStatusExceptionSupport;

/**
 * CoinDeal API exception
 *
 * <p>CoinDeals error response format is wildly inconsistent - its like totally different in various
 * situations. As a consiquence this class will have many properties that are sometimes set and
 * sometimes null.
 */
public class CoindealException extends HttpStatusExceptionSupport {

  @JsonProperty("errors")
  private JsonNode errors;

  /**
   * Used by the exchange for validation errors returned with HTTP code 422
   *
   * <p>The structure of this field seams to be very flexible
   */
  public JsonNode getErrors() {
    return errors;
  }

  public void setErrors(JsonNode errors) {
    this.errors = errors;
  }
}
