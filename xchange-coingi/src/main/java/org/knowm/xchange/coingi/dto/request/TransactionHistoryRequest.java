package org.knowm.xchange.coingi.dto.request;

import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.currency.CurrencyPair;

public class TransactionHistoryRequest extends AuthenticatedRequest {
  private int pageNumber;
  private int pageSize;
  private int type;
  private String currencyPair;
  private int status;

  public int getPageNumber() {
    return pageNumber;
  }

  public TransactionHistoryRequest setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public TransactionHistoryRequest setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public Integer getType() {
    return type;
  }

  public TransactionHistoryRequest setType(Integer type) {
    this.type = type;
    return this;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public TransactionHistoryRequest setCurrencyPair(CurrencyPair currencyPair) {
    if (currencyPair != null) this.currencyPair = CoingiAdapters.adaptCurrency(currencyPair);

    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public TransactionHistoryRequest setStatus(Integer status) {
    this.status = status;
    return this;
  }
}
