package org.knowm.xchange.dase.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an error response from the DASE API.
 *
 * <p>All error responses (except 500) include a JSON body with type and message fields.
 */
public class DaseErrorResponse {

  private final String type;
  private final String message;

  @JsonCreator
  public DaseErrorResponse(
      @JsonProperty("type") String type, @JsonProperty("message") String message) {
    this.type = type;
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "DaseErrorResponse{type='" + type + "', message='" + message + "'}";
  }
}
