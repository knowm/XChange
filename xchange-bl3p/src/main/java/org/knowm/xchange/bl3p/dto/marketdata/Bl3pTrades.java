package org.knowm.xchange.bl3p.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bl3p.dto.Bl3pResult;
import org.knowm.xchange.bl3p.dto.Bl3pTrade;

public class Bl3pTrades extends Bl3pResult<Bl3pTrades.Bl3pTradesData> {

  public static class Bl3pTradesData {

    @JsonProperty("trades")
    private Bl3pTrade[] trades;

    public Bl3pTrade[] getTrades() {
      return trades;
    }
  }
}
