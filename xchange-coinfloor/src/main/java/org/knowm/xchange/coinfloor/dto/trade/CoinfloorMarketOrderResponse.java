package org.knowm.xchange.coinfloor.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinfloorMarketOrderResponse {
  private final BigDecimal remaining;

  public CoinfloorMarketOrderResponse(@JsonProperty("remaining") BigDecimal remaining) {
    this.remaining = remaining;
  }

  public BigDecimal getRemaining() {
    return remaining;
  }

  @Override
  public String toString() {
    return String.format("MarketOrderResponse{remaining=%s}", remaining);
  }
}
