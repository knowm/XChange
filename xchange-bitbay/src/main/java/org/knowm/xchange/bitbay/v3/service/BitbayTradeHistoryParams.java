package org.knowm.xchange.bitbay.v3.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;

/** @author walec51 */
public class BitbayTradeHistoryParams
    implements TradeHistoryParamLimit, TradeHistoryParamNextPageCursor {

  public static final String START = "start"; // special value that enables paging

  public BitbayTradeHistoryParams() {
    this(START, 300);
  }

  public BitbayTradeHistoryParams(String nextPageCursor, Integer limit) {
    this.nextPageCursor = nextPageCursor;
    this.limit = limit;
  }

  /** Use this factory method to enable paging. */
  public static BitbayTradeHistoryParams startBitBayTradeHistoryParams(Integer limit) {
    return new BitbayTradeHistoryParams(START, limit);
  }

  /**
   * In order to get a cursor that enables paging you need initailly set this to the value 'start'
   */
  private String nextPageCursor;

  private Integer limit;

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
