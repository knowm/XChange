package org.knowm.xchange.latoken.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;

public class LatokenTradeHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamLimit {

  private CurrencyPair currencyPair;

  /** Optional parameter. Defines the maximal number of trades in the response. */
  private Integer limit;

  public LatokenTradeHistoryParams() {}

  public LatokenTradeHistoryParams(CurrencyPair currencyPair, Integer limit) {
    this.currencyPair = currencyPair;
    this.limit = limit;
  }

  public LatokenTradeHistoryParams(CurrencyPair currencyPair) {
    this(currencyPair, null);
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
