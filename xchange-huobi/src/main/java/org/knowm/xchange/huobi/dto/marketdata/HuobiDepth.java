package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  public HuobiDepth(@JsonProperty("asks") final BigDecimal[][] asks, @JsonProperty("bids") final BigDecimal[][] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BigDecimal[][] getAsks() {

    return asks;
  }

  public BigDecimal[][] getBids() {

    return bids;
  }

}
