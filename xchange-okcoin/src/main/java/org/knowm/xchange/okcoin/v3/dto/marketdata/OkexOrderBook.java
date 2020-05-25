package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import lombok.Data;

@Data
public class OkexOrderBook {

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("bids")
  private OkexOrderBookEntry[] bids;

  @JsonProperty("asks")
  private OkexOrderBookEntry[] asks;

  public String getTimestamp() {

    return timestamp;
  }

  public OkexOrderBookEntry[] getBids() {

    return bids;
  }

  public OkexOrderBookEntry[] getAsks() {

    return asks;
  }

  @Override
  public String toString() {
    return "OkexOrderBook [timestamp="
        + timestamp
        + ", bids="
        + Arrays.toString(bids)
        + ", asks="
        + Arrays.toString(asks)
        + "]";
  }
}
