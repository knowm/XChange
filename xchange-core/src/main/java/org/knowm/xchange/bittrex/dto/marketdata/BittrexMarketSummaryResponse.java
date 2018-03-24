package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexMarketSummaryResponse {

  private final boolean success;
  private final String message;
  private final BittrexMarketSummary marketSummary;

  public BittrexMarketSummaryResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") ArrayList<BittrexMarketSummary> result) {

    super();
    this.success = success;
    this.message = message;
    this.marketSummary = result.get(0);
  }

  public boolean getSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public BittrexMarketSummary getMarketSummary() {

    return marketSummary;
  }

  @Override
  public String toString() {

    return "BittrexMarketSummaryResponse [success=" + success + ", message=" + message + ", marketSummary=" + marketSummary + "]";
  }

}
