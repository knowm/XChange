package org.knowm.xchange.coingi.dto.request;

import org.knowm.xchange.currency.CurrencyPair;

public class GetOrderHistoryRequest extends AuthenticatedRequest {
  private int pageNumber;
  private int pageSize;
  private int type;
  private String currencyPair;
  private Integer status;

  public int getPageNumber() {
    return pageNumber;
  }

  public GetOrderHistoryRequest setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public GetOrderHistoryRequest setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public Integer getType() {
    return type;
  }

  public GetOrderHistoryRequest setType(Integer type) {
    this.type = type;
    return this;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public GetOrderHistoryRequest setCurrencyPair(CurrencyPair currencyPair) {
    if (currencyPair != null)
      this.currencyPair = currencyPair.toString().replace('/', '-').toLowerCase();

    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public GetOrderHistoryRequest setStatus(Integer status) {
    this.status = status;
    return this;
  }
}
