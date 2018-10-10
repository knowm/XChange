package org.knowm.xchange.bl3p.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bl3p.dto.Bl3pTrade;

public class Bl3pUserTrades {

  public static class Bl3pUserTradesData {
    @JsonProperty("trades")
    private Bl3pTrade[] trades;

    public Bl3pTrade[] getTrades() {
      return trades;
    }
  }
}
