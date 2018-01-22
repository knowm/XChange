package org.knowm.xchange.kucoin;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusExceptionSupport;

/**
 * @author Jan Akerman
 */
public class KucoinException extends HttpStatusExceptionSupport {

  private final Long timestamp;
  private final int status;
  private final String error;
  private final String message;
  private final String path;

  public KucoinException(
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("status") int status,
      @JsonProperty("error") String error,
      @JsonProperty("message") String message,
      @JsonProperty("path") String path
  ) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }
}
