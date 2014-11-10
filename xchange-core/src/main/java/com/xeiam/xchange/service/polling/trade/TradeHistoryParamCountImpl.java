package com.xeiam.xchange.service.polling.trade;

/**
 * Common implementation of {@link TradeHistoryParamCount} interface
 */
public class TradeHistoryParamCountImpl implements TradeHistoryParamCount {
  private Integer count;

  public TradeHistoryParamCountImpl() {
  }

  public TradeHistoryParamCountImpl(Integer count) {
    this.count = count;
  }

  @Override
  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public Integer getCount() {
    return count;
  }
}
