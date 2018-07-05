package org.knowm.xchange.cobinhood;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cobinhood.dto.CobinhoodError;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class CobinhoodException extends HttpStatusExceptionSupport {
  private Boolean success;
  private CobinhoodError error;
  private String requestId;

  public CobinhoodException(
      @JsonProperty("success") Boolean success,
      @JsonProperty("error") CobinhoodError error,
      @JsonProperty("request_id") String requestId) {

    this.success = success;
    this.error = error;
    this.requestId = requestId;
  }

  @Override
  public String toString() {
    return "CobinhoodException{"
        + "success="
        + success
        + ", error="
        + error
        + ", requestId='"
        + requestId
        + '\''
        + '}';
  }
}
