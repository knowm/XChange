package org.knowm.xchange.abucoins.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;

/** @author bryant_harris */
public class AbucoinsTradeHistoryParams
    implements TradeHistoryParamLimit, TradeHistoryParamNextPageCursor {

  private Integer limit;
  private String nextPageCursor;

  @Override
  public Integer getLimit() {
    return limit;
  }

  /** API defaults do 100 if nothing is set. Max 1000. */
  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public String getNextPageCursor() {
    return nextPageCursor;
  }

  @Override
  public void setNextPageCursor(String nextPageCursor) {
    this.nextPageCursor = nextPageCursor;
  }
}
