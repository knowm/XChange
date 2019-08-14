package org.knowm.xchange.lgo.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.*;

public class LgoTradeHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamCurrencyPair,
        TradeHistoryParamLimit,
        TradeHistoryParamNextPageCursor,
        TradeHistoryParamsSorted {

  private CurrencyPair currencyPair = CurrencyPair.BTC_USD;
  private Integer limit = 100;
  private String pageCursor = null;
  private Order order = Order.desc;

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
    this.currencyPair = pair;
  }

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
    return pageCursor;
  }

  @Override
  public void setNextPageCursor(String cursor) {
    this.pageCursor = cursor;
  }

  @Override
  public Order getOrder() {
    return order;
  }

  @Override
  public void setOrder(Order order) {
    this.order = order;
  }
}
