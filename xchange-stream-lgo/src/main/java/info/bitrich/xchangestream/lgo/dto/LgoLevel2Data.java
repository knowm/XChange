package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoLevel2Data {

  private final List<List<String>> bids;
  private final List<List<String>> asks;

  public LgoLevel2Data(
      @JsonProperty("bids") List<List<String>> bids,
      @JsonProperty("asks") List<List<String>> asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public List<List<String>> getBids() {
    return bids;
  }

  public List<List<String>> getAsks() {
    return asks;
  }
}
