package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexMarketSummariesResponse {

  private final boolean success;
  private final String message;
  private final ArrayList<BittrexMarketSummary> marketSummaries;

  public BittrexMarketSummariesResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") ArrayList<BittrexMarketSummary> result) {

    super();
    this.success = success;
    this.message = message;
    this.marketSummaries = result;
  }

  public boolean isSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public ArrayList<BittrexMarketSummary> getMarketSummaries() {

    return marketSummaries;
  }

  @Override
  public String toString() {

    return "BittrexMarketSummariesResponse [success=" + success + ", message=" + message + ", marketSummaries=" + marketSummaries + "]";
  }

}
