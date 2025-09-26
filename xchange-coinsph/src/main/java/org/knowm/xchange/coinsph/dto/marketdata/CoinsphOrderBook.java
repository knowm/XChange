package org.knowm.xchange.coinsph.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphOrderBook {

  private final long lastUpdateId;
  private final List<CoinsphOrderBookEntry> bids;
  private final List<CoinsphOrderBookEntry> asks;

  public CoinsphOrderBook(
      @JsonProperty("lastUpdateId") long lastUpdateId,
      @JsonProperty("bids") List<CoinsphOrderBookEntry> bids,
      @JsonProperty("asks") List<CoinsphOrderBookEntry> asks) {
    this.lastUpdateId = lastUpdateId;
    this.bids = bids;
    this.asks = asks;
  }
}
