package org.knowm.xchange.lgo.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LgoOrderbook {

  public final long lastBatchId;
  public final LgoMarket market;

  public LgoOrderbook(
      @JsonProperty("batchId") long lastBatchId, @JsonProperty("result") LgoMarket market) {
    this.lastBatchId = lastBatchId;
    this.market = market;
  }
}
