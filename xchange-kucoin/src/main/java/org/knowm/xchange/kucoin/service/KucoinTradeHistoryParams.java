package org.knowm.xchange.kucoin.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

public class KucoinTradeHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging {
  
  private CurrencyPair currencyPair;
  private Integer pageLength;
  private Integer pageNumber;

  @Override
  public void setPageLength(Integer pageLength) {
    
    this.pageLength = pageLength;
  }

  @Override
  public Integer getPageLength() {
    return pageLength;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    
    this.pageNumber = pageNumber;
  }

  @Override
  public Integer getPageNumber() {
    
    return pageNumber;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
    
    this.currencyPair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

}
