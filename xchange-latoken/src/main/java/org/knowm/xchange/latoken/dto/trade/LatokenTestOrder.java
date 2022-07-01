package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"success": true,
 * 	"message": "All good!"
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenTestOrder {

  private final boolean success;
  private final String message;

  public LatokenTestOrder(
      @JsonProperty("success") boolean success, @JsonProperty("message") String message) {

    this.success = success;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "LatokenTestOrder [success = " + success + ", message = " + message + "]";
  }
}
