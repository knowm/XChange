package org.knowm.xchange.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BTCMarketsException extends HttpStatusExceptionSupport {

  private final Boolean success;
  private final Integer errorCode;
  private final String clientRequestId;
  private final Long id;
  private final List<BTCMarketsException> responses;

  public BTCMarketsException(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("clientRequestId") String clientRequestId,
      @JsonProperty("id") Long id,
      @JsonProperty("responses") List<BTCMarketsException> responses) {
    super(constructMsg(errorMessage, responses));
    this.success = success;
    this.errorCode = errorCode;
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
        if (!Boolean.TRUE.equals(response.getSuccess())) {
          sb.append(String.format("Id %d: %s", response.getId(), response.getMessage()));
        }
      }
    }
    return sb.toString();
  }

  public Boolean getSuccess() {
    return success;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public String getClientRequestId() {
    return clientRequestId;
  }

  public Long getId() {
    return id;
  }

  public List<BTCMarketsException> getResponses() {
    return responses;
  }
}
