package org.knowm.xchange.coindcx.dto;

import java.util.List;

public class CoindcxTrades {

  private List<CoindcxTrade> coindcxTrades;

  public List<CoindcxTrade> getCoindcxTrades() {
    return coindcxTrades;
  }

  public void setCoindcxTrades(List<CoindcxTrade> coindcxTrades) {
    this.coindcxTrades = coindcxTrades;
  }

  @Override
  public String toString() {
    return "CoindcxTrades [coindcxTrades=" + coindcxTrades + "]";
  }
}
