package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

public class OkexOrderbook {

  private final List<OkexPublicOrder> asks;

  private final List<OkexPublicOrder> bids;

  private final String ts;

  private final long checksum;

  @JsonCreator
  public OkexOrderbook(
      @JsonProperty("asks") List<OkexPublicOrder> asks,
      @JsonProperty("bids") List<OkexPublicOrder> bids,
      @JsonProperty("ts") String ts,
      @JsonProperty("checksum") long checksum) {

    this.asks = asks;
    this.bids = bids;
    this.ts = ts;
    this.checksum = checksum;
  }

  public List<OkexPublicOrder> getAsks() {
    return Collections.synchronizedList(asks);
  }

  public List<OkexPublicOrder> getBids() {
    return Collections.synchronizedList(bids);
  }

  public String getTs() {
    return ts;
  }

  public long getChecksum() {
    return checksum;
  }

  @Override
  public String toString() {
    return "OkexOrderbookResponse{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
