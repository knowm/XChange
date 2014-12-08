package com.xeiam.xchange.dto.trade;

import java.util.List;

import com.xeiam.xchange.dto.marketdata.Trades;

@SuppressWarnings("unchecked")
public class UserTrades extends Trades {

  public UserTrades(List<UserTrade> trades, TradeSortType tradeSortType) {

    super((List)trades, tradeSortType);
  }

  public UserTrades(List<UserTrade> trades, long lastID, TradeSortType tradeSortType) {

    super((List)trades, lastID, tradeSortType);
  }

  public List<UserTrade> getUserTrades() {

    return (List)getTrades();
  }
}
