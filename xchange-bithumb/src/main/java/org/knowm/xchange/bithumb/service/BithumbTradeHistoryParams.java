package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

public class BithumbTradeHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging {

  private Integer pageLength;
  private Integer pageNumber;
  private CurrencyPair currencyPair;

  @Override
  public Integer getPageLength() {
    return this.pageLength;
  }

  @Override
  public void setPageLength(Integer pageLength) {
    this.pageLength = pageLength;
  }

  @Override
  public Integer getPageNumber() {
    return this.pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
    this.currencyPair = pair;
  }
}
