package org.knowm.xchange.bitbay.v3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author walec51 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitbayBaseResponse {

  private final String status;
  private final List<String> errors;

  public BitbayBaseResponse(
      @JsonProperty("status") String status, @JsonProperty("errors") List<String> errors) {
    this.status = status;
    this.errors = errors;
  }

  public String getStatus() {
    return status;
  }

  public List<String> getErrors() {
    return errors;
  }
}
