package org.knowm.xchange.latoken.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"error": {
 * 		"message": "string",
 * 		"errorType": "string",
 * 		"statusCode": 0
 * 	}
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenException extends HttpStatusExceptionSupport {

  private static final long serialVersionUID = -3780202375788517773L;

  public static class LatokenError {
    String message;
    String errorType;
    int statusCode;

    public LatokenError(
        @JsonProperty("message") String message,
        @JsonProperty("errorType") String errorType,
        @JsonProperty("statusCode") int statusCode) {

      this.message = message;
      this.errorType = errorType;
      this.statusCode = statusCode;
    }
  }

  private final String errorType;

  /**
   * C'tor
   *
   * @param error
   */
  public LatokenException(@JsonProperty("error") LatokenError error) {
    super(error.message);
    setHttpStatusCode(error.statusCode);
    this.errorType = error.errorType;
  }

  public String getErrorType() {
    return errorType;
  }
}
