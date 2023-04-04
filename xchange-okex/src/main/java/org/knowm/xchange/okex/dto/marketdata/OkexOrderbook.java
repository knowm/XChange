package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class OkexOrderbook {

  @Getter private final List<OkexPublicOrder> asks;
  @Getter private final List<OkexPublicOrder> bids;
  @Getter private final String ts;

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
