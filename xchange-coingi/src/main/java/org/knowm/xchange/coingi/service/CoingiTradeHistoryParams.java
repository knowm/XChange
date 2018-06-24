package org.knowm.xchange.coingi.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class CoingiTradeHistoryParams implements TradeHistoryParams {
  private CurrencyPair currencyPair;
  private Integer pageNumber;
  private Integer pageSize;
  private Integer type;
  private Integer status;

  public CoingiTradeHistoryParams(
      CurrencyPair currencyPair,
      Integer pageNumber,
      Integer pageSize,
      Integer type,
      Integer status) {
    this.currencyPair = currencyPair;
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.type = type;
    this.status = status;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
