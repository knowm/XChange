package org.knowm.xchange.huobi.dto.marketdata.futures;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcFuturesDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  public BitVcFuturesDepth(@JsonProperty("asks") final BigDecimal[][] asks, @JsonProperty("bids") final BigDecimal[][] bids) {

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
