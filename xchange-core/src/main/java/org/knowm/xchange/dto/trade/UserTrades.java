package org.knowm.xchange.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dto.marketdata.Trades;

public class UserTrades extends Trades {

  private static final long serialVersionUID = 1647451200702821967L;

  public UserTrades(
      @JsonProperty("trades") List<UserTrade> trades,
      @JsonProperty("tradeSortType") TradeSortType tradeSortType) {

    super((List) trades, tradeSortType);
  }

  public UserTrades(List<UserTrade> trades, long lastID, TradeSortType tradeSortType) {

    super((List) trades, lastID, tradeSortType);
  }

  public UserTrades(
      List<UserTrade> trades, long lastID, TradeSortType tradeSortType, String nextPageCursor) {
    super((List) trades, lastID, tradeSortType, nextPageCursor);
  }

  @JsonIgnore
  public List<UserTrade> getUserTrades() {

    return (List) getTrades();
  }
}
