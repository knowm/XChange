package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BankeraTradesResponse {

  private final List<BankeraTrade> trades;

  public BankeraTradesResponse(@JsonProperty("deals") List<BankeraTrade> trades) {
    this.trades = trades;
  }

  public List<BankeraTrade> getTrades() {
    return trades;
  }
}
