package org.knowm.xchange.bitbay.v3.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;

/** @author walec51 */
public class BitbayTradeHistoryParams
    implements TradeHistoryParamLimit, TradeHistoryParamNextPageCursor {

  /**
   * This starts from the newest users trades. You must set it to this value if you want to receive
   * the next cursor for older trades.
   */
  private String nextPageCursor = "start";

  private Integer limit = 300;

  @Override
  public Integer getLimit() {
    return limit;
  }

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
