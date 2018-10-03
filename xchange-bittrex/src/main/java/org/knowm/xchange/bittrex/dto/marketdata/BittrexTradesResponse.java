package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BittrexTradesResponse {

  private final boolean success;
  private final String message;
  private final BittrexTrade[] trades;

  public BittrexTradesResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("message") String message,
      @JsonProperty("result") BittrexTrade[] trades) {

    super();
    this.success = success;
    this.message = message;
    this.trades = trades;
  }

  public boolean getSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public BittrexTrade[] getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    return "BittrexTradesResponse [success="
        + success
        + ", message="
        + message
        + ", trades="
        + Arrays.toString(trades)
        + "]";
  }
}
