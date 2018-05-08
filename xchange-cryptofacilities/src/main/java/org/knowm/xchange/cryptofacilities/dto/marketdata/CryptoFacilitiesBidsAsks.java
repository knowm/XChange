package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/** @author Panchen */
public class CryptoFacilitiesBidsAsks {

  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  public CryptoFacilitiesBidsAsks(
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("bids") List<List<BigDecimal>> bids) {

    this.bids = bids;
    this.asks = asks;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  @Override
  public String toString() {

    return "[bids=" + bids + ", asks=" + asks + "]";
  }
}
