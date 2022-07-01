package org.knowm.xchange.bankera.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BankeraUserTrades {

  private final List<BankeraUserTrade> trades;

  public BankeraUserTrades(@JsonProperty("trades") List<BankeraUserTrade> trades) {

    this.trades = trades;
  }

  public List<BankeraUserTrade> getTrades() {
    return trades;
  }
}
