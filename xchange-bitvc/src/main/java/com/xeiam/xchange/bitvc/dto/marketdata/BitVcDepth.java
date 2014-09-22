package com.xeiam.xchange.bitvc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  public BitVcDepth(@JsonProperty("asks") final BigDecimal[][] asks, @JsonProperty("bids") final BigDecimal[][] bids) {

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
