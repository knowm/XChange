package org.knowm.xchange.coinsetter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Coinsetter generic response.
 */
public class CoinsetterResponse {

  private final String message;
  private final String requestStatus;

  /**
   * @param message If success, message will be "OK". Otherwise, it will be a description of the issue.
   * @param requestStatus Either "SUCCESS" or "FAILURE"
   */
  public CoinsetterResponse(@JsonProperty("message") String message, @JsonProperty("requestStatus") String requestStatus) {

    this.message = message;
    this.requestStatus = requestStatus;
  }

  public String getMessage() {

    return message;
  }

  public String getRequestStatus() {

    return requestStatus;
  }

  @Override
  public String toString() {

    return "CoinsetterResponse [message=" + message + ", requestStatus=" + requestStatus + "]";
  }

}
