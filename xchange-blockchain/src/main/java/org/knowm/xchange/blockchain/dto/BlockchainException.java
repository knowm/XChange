package org.knowm.xchange.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BlockchainException extends HttpStatusExceptionSupport implements HttpResponseAware {

  private final int status;

  private Map<String, List<String>> headers;

  public BlockchainException(
      @JsonProperty("status") int status, @JsonProperty("message") String message) {
    super(message);
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }
}
