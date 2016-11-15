package org.knowm.xchange.hitbtc.dto.trade;

import java.util.Arrays;

import org.knowm.xchange.hitbtc.dto.HitbtcBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcTradeResponse extends HitbtcBaseResponse {

  private final HitbtcOwnTrade[] trades;

  public HitbtcTradeResponse(@JsonProperty("trades") HitbtcOwnTrade[] trades) {

    this.trades = trades;
  }

  public HitbtcOwnTrade[] getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcTradeResponse [trades=");
    builder.append(Arrays.toString(trades));
    builder.append("]");
    return builder.toString();
  }
}
