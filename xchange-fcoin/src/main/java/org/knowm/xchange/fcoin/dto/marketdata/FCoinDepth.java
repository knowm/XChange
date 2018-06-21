package org.knowm.xchange.fcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class FCoinDepth {
  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;
  private final Date ts;
  private final Long seq;

  public FCoinDepth(
      @JsonProperty("asks") BigDecimal[] asks,
      @JsonProperty("bids") BigDecimal[] bids,
      @JsonProperty(required = false, value = "ts") Long ts,
      @JsonProperty(required = false, value = "seq") Long seq) {

    this.asks = new BigDecimal[asks.length / 2][];
    for (int i = 0; i < asks.length / 2; i++) {
      this.asks[i] = new BigDecimal[2];
      this.asks[i][0] = asks[i * 2];
      this.asks[i][1] = asks[i * 2 + 1];
    }
    this.bids = new BigDecimal[bids.length / 2][];
    for (int i = 0; i < asks.length / 2; i++) {
      this.bids[i] = new BigDecimal[2];
      this.bids[i][0] = bids[i * 2];
      this.bids[i][1] = bids[i * 2 + 1];
    }
    this.ts = new Date(ts);
    this.seq = seq;
  }

  public BigDecimal[][] getAsks() {
    return this.asks;
  }

  public BigDecimal[][] getBids() {
    return this.bids;
  }

  public Date getTs() {
    return ts;
  }

  public Long getSeq() {
    return seq;
  }

  public String toString() {
    return "FcoinDepth [asks="
        + Arrays.toString(this.asks)
        + ", bids="
        + Arrays.toString(this.bids)
        + "]";
  }
}
