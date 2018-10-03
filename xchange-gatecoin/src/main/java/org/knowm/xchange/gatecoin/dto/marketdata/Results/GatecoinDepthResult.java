package org.knowm.xchange.gatecoin.dto.marketdata.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinDepth;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

/** @author sumdeha */
public class GatecoinDepthResult extends GatecoinResult {

  private final GatecoinDepth[] asks;

  private final GatecoinDepth[] bids;

  @JsonCreator
  public GatecoinDepthResult(
      @JsonProperty("asks") GatecoinDepth[] asks,
      @JsonProperty("bids") GatecoinDepth[] bids,
      @JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
    this.asks = asks;
    this.bids = bids;
  }

  public GatecoinDepth[] getAsks() {
    return this.asks;
  }

  public GatecoinDepth[] getBids() {
    return this.bids;
  }
}
