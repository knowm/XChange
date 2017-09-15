package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexDepthResponse {

  private final boolean success;
  private final String message;
  private final BittrexDepth depth;

  public BittrexDepthResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") BittrexDepth depth) {

    this.success = success;
    this.message = message;
    this.depth = depth;
  }

  public boolean getSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public BittrexDepth getDepth() {

    return depth;
  }

  @Override
  public String toString() {

    return "BittrexDepthResponse [success=" + success + ", message=" + message + ", depth=" + depth + "]";
  }

}
