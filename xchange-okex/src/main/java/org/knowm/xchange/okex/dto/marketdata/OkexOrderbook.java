package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class OkexOrderbook {

  private final List<OkexPublicOrder> asks;

  private final List<OkexPublicOrder> bids;
  private final String ts;

  @JsonCreator
  public OkexOrderbook(
      @JsonProperty("asks") List<OkexPublicOrder> asks,
      @JsonProperty("bids") List<OkexPublicOrder> bids,
      @JsonProperty("ts") String ts) {

    this.asks = asks;
    this.bids = bids;
    this.ts = ts;
  }

  @Override
  public String toString() {
    return "OkexOrderbookResponse{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
