package org.knowm.xchange.dsx.dto;

import org.knowm.xchange.dto.marketdata.Trades;

public enum DsxTradesSortBy {
  id(Trades.TradeSortType.SortByID),
  timestamp(Trades.TradeSortType.SortByTimestamp);

  public final Trades.TradeSortType sortType;

  DsxTradesSortBy(Trades.TradeSortType sortType) {
    this.sortType = sortType;
  }
}
