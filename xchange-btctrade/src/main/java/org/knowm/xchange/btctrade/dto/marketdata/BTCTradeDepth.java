package org.knowm.xchange.btctrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCTradeDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  public BTCTradeDepth(
      @JsonProperty("asks") BigDecimal[][] asks, @JsonProperty("bids") BigDecimal[][] bids) {

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
