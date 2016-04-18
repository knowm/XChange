package org.knowm.xchange.chbtc.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

public class ChbtcOrderBook {

  private List<List<BigDecimal>> bids;

  private List<List<BigDecimal>> asks;

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }
}
