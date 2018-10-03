package org.knowm.xchange.coingi.dto.trade;

import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;
import org.knowm.xchange.currency.CurrencyPair;

public class CoingiTransactionHistoryRequest extends CoingiAuthenticatedRequest {
  private int pageNumber;
  private int pageSize;
  private int type;
  private String currencyPair;
  private int status;

  public int getPageNumber() {
    return pageNumber;
  }

  public CoingiTransactionHistoryRequest setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public CoingiTransactionHistoryRequest setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public Integer getType() {
    return type;
  }

  public CoingiTransactionHistoryRequest setType(Integer type) {
    this.type = type;
    return this;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public CoingiTransactionHistoryRequest setCurrencyPair(CurrencyPair currencyPair) {
    if (currencyPair != null) this.currencyPair = CoingiAdapters.adaptCurrency(currencyPair);

    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public CoingiTransactionHistoryRequest setStatus(Integer status) {
    this.status = status;
    return this;
  }
}
