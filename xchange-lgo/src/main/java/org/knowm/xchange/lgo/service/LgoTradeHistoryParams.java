package org.knowm.xchange.lgo.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class LgoTradeHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamInstrument,
        TradeHistoryParamLimit,
        TradeHistoryParamNextPageCursor,
        TradeHistoryParamsSorted {

  private Instrument currencyPair = CurrencyPair.BTC_USD;
  private Integer limit = 100;
  private String pageCursor = null;
  private Order order = Order.desc;

  @Override
  public Instrument getInstrument() {
    return currencyPair;
  }

  @Override
  public void setInstrument(Instrument pair) {
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
