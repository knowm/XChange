package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FtxOrderbookDto {

  private final List<FtxPublicOrder> asks;

  private final List<FtxPublicOrder> bids;

  @JsonCreator
  public FtxOrderbookDto(
      @JsonProperty("asks") List<FtxPublicOrder> asks,
      @JsonProperty("bids") List<FtxPublicOrder> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public List<FtxPublicOrder> getAsks() {
    return asks;
  }

  public List<FtxPublicOrder> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "FtxOrderbookResponse{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
