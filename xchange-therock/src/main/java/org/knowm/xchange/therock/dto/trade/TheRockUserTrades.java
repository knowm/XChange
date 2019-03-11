package org.knowm.xchange.therock.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** https://api.therocktrading.com/doc/v1/index.html#api-Trading_API-Trades */
public class TheRockUserTrades {

  private final TheRockUserTrade[] trades;

  public TheRockUserTrades(
      @JsonProperty("trades") TheRockUserTrade[] trades, @JsonProperty("meta") Object ignored) {
    this.trades = trades;
  }

  public int getCount() {
    return trades.length;
  }

  public TheRockUserTrade[] getTrades() {
    return trades;
  }
}
