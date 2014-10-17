package com.xeiam.xchange.okcoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  public OkCoinDepth(@JsonProperty("asks") final BigDecimal[][] asks, @JsonProperty("bids") final BigDecimal[][] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BigDecimal[][] getAsks() {

    return asks;
  }

  public BigDecimal[][] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "OkCoinDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }

}
