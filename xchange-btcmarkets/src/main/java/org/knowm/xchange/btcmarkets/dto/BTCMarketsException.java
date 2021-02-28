package org.knowm.xchange.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BTCMarketsException extends HttpStatusExceptionSupport {

  public final Boolean success;
  public final Integer errorCode;
  public final String errorMessage;
  public final String clientRequestId;
  public final Long id;
  public final List<BTCMarketsException> responses;

  public BTCMarketsException(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("clientRequestId") String clientRequestId,
      @JsonProperty("id") Long id,
      @JsonProperty("responses") List<BTCMarketsException> responses,
      // V3
      @JsonProperty("code") String code,
      @JsonProperty("message") String message) {
    super(constructMsg(errorMessage, responses));
    this.success = success;
    this.errorCode = errorCode;
    if (errorMessage != null) {
      this.errorMessage = errorMessage;
    } else {
      this.errorMessage = message;
    }
    this.clientRequestId = clientRequestId;
    this.id = id;
    this.responses = responses;
  }

  private static String constructMsg(String errorMessage, List<BTCMarketsException> responses) {
    final StringBuilder sb = new StringBuilder();
    if (errorMessage != null) {
      sb.append(errorMessage).append(": ");
    }
    if (responses != null) {
      for (BTCMarketsException response : responses) {
        if (!Boolean.TRUE.equals(response.success)) {
          sb.append(String.format("Id %d: %s", response.id, response.getMessage()));
        }
      }
    }
    return sb.toString();
  }
}
