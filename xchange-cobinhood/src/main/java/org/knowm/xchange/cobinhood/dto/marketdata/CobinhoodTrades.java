package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CobinhoodTrades {
  private final List<CobinhoodTrade> trades;

  public CobinhoodTrades(@JsonProperty("trades") List<CobinhoodTrade> trades) {
    this.trades = trades;
  }

  public List<CobinhoodTrade> getTrades() {
    return trades;
  }
}
