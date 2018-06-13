package org.knowm.xchange.coingi.dto.trade;

import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;
import org.knowm.xchange.currency.CurrencyPair;

public class CoingiGetOrderHistoryRequest extends CoingiAuthenticatedRequest {
  private int pageNumber;
  private int pageSize;
  private int type;
  private String currencyPair;
  private Integer status;

  public int getPageNumber() {
    return pageNumber;
  }

  public CoingiGetOrderHistoryRequest setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public CoingiGetOrderHistoryRequest setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public Integer getType() {
    return type;
  }

  public CoingiGetOrderHistoryRequest setType(Integer type) {
    this.type = type;
    return this;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public CoingiGetOrderHistoryRequest setCurrencyPair(CurrencyPair currencyPair) {
    if (currencyPair != null)
      this.currencyPair = currencyPair.toString().replace('/', '-').toLowerCase();

    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public CoingiGetOrderHistoryRequest setStatus(Integer status) {
    this.status = status;
    return this;
  }
}
