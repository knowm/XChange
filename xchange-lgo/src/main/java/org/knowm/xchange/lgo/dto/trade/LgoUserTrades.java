package org.knowm.xchange.lgo.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoUserTrades {

  private final List<LgoUserTrade> trades;

  public LgoUserTrades(@JsonProperty("trades") List<LgoUserTrade> trades) {
    this.trades = trades;
  }

  public List<LgoUserTrade> getTrades() {
    return trades;
  }
}
