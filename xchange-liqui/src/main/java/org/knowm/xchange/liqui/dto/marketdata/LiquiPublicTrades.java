package org.knowm.xchange.liqui.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LiquiPublicTrades {

  private final List<LiquiPublicTrade> trades;

  @JsonCreator
  public LiquiPublicTrades(final List<LiquiPublicTrade> trades) {
    this.trades = trades;
  }

  public List<LiquiPublicTrade> getTrades() {
    return trades;
  }

  @Override
  public String toString() {
    return "LiquiPublicTrades{" +
        "trades=" + trades +
        '}';
  }
}
