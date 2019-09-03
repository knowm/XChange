package org.knowm.xchange.dto.trade;

import java.util.List;
import org.knowm.xchange.dto.marketdata.Trades;

public class UserTrades extends Trades {

  private static final long serialVersionUID = 1647451200702821967L;

  public UserTrades(List<UserTrade> trades, TradeSortType tradeSortType) {

    super((List) trades, tradeSortType);
  }

  public UserTrades(List<UserTrade> trades, long lastID, TradeSortType tradeSortType) {

    super((List) trades, lastID, tradeSortType);
  }

  public UserTrades(
      List<UserTrade> trades, long lastID, TradeSortType tradeSortType, String nextPageCursor) {
    super((List) trades, lastID, tradeSortType, nextPageCursor);
  }

  public List<UserTrade> getUserTrades() {

    return (List) getTrades();
  }
}
