package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AscendexOrderbookDto {

  private final String m;

  private final String symbol;

  private final AscendexOrderbookData data;

  public AscendexOrderbookDto(
      @JsonProperty("m") String m,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("data") AscendexOrderbookData data) {
    this.m = m;
    this.symbol = symbol;
    this.data = data;
  }

  public String getM() {
    return m;
  }

  public String getSymbol() {
    return symbol;
  }

  public AscendexOrderbookData getData() {
    return data;
  }

  @Override
  public String toString() {
    return "AscendexOrderbookDto{"
        + "m='"
        + m
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", data="
        + data
        + '}';
  }

  public static class AscendexOrderbookData {
    private final Long seqnum;

    private final Date ts;

    private final List<AscendexPublicOrder> asks;

    private final List<AscendexPublicOrder> bids;

    public AscendexOrderbookData(
        @JsonProperty("seqnum") Long seqnum,
        @JsonProperty("ts") Long ts,
        @JsonProperty("asks") List<AscendexPublicOrder> asks,
        @JsonProperty("bids") List<AscendexPublicOrder> bids) {
      this.seqnum = seqnum;
      this.ts = Date.from(Instant.ofEpochMilli(ts));
      this.asks = asks;
      this.bids = bids;
    }

    public Long getSeqnum() {
      return seqnum;
    }

    public Date getTimestamp() {
      return ts;
    }

    public List<AscendexPublicOrder> getAsks() {
      return asks;
    }

    public List<AscendexPublicOrder> getBids() {
      return bids;
    }

    @Override
    public String toString() {
      return "AscendexOrderbookData{"
          + "seqnum="
          + seqnum
          + ", timestamp="
          + ts
          + ", asks="
          + asks
          + ", bids="
          + bids
          + '}';
    }
  }
}
