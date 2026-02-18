package org.knowm.xchange.dase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.dase.DaseErrorAdapter;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.HttpResponseAware;
import si.mazi.rescu.HttpStatusExceptionSupport;

/**
 * Exception class for DASE API errors that can be deserialized from HTTP error responses.
 *
 * <p>Rescu automatically deserializes error responses into this class when the API returns an error
 * with a JSON body containing 'type' and 'message' fields.
 */
public class DaseApiException extends HttpStatusExceptionSupport implements HttpResponseAware {

  private final String type;
  private final String message;
  private Map<String, List<String>> headers;

  public DaseApiException(
      @JsonProperty("type") String type, @JsonProperty("message") String message) {
    super(message);
    this.type = type;
    this.message = message;
  }

  public String getType() {
    return type;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  /**
   * Converts this API exception to an appropriate XChange exception type based on the error type.
   *
   * @return an appropriate XChange exception
   */
  public ExchangeException toExchangeException() {
    return DaseErrorAdapter.adapt(type, getMessage());
  }
}
