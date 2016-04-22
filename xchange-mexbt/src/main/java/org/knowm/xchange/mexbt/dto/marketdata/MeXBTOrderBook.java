package org.knowm.xchange.mexbt.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTOrderBook {

  private final BigDecimal[][] bids;
  private final BigDecimal[][] asks;

  public MeXBTOrderBook(@JsonProperty("bids") BigDecimal[][] bids, @JsonProperty("asks") BigDecimal[][] asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public BigDecimal[][] getBids() {
    return bids;
  }

  public BigDecimal[][] getAsks() {
    return asks;
  }

}
