package org.knowm.xchange.virtex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class VirtExDepthWrapper {

  private final VirtExDepth depth;
  private final String status;
  private final String message;
  private final String apirate;

  /**
   * Constructor
   * 
   * @param depth
   * @param status
   * @param message
   * @param apirate
   */
  public VirtExDepthWrapper(@JsonProperty("orderbook") VirtExDepth depth, @JsonProperty("message") String message,
      @JsonProperty("status") String status, @JsonProperty("apirate") String apirate) {

    this.depth = depth;
    this.status = status;
    this.message = message;
    this.apirate = apirate;
  }

  public VirtExDepth getDepth() {

    return depth;
  }

  public String getStatus() {

    return status;
  }

  public String getMessage() {

    return message;
  }

  public String getApiRate() {

    return apirate;
  }

  @Override
  public String toString() {

    return "VirtExDepth [depth" + depth + ", status=" + status + ", message=" + message + ", apirate=" + apirate + "]";

  }

}
