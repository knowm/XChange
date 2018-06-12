package org.knowm.xchange.coingi.dto.request;

import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Optional;

public class TransactionHistoryRequest extends AuthenticatedRequest {
  private int pageNumber;
  private int pageSize;
  private Optional<Integer> type;
  private Optional<CoingiAuthenticated.Pair> currencyPair;
  private Optional<Integer> status;

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

  public Optional<Integer> getType() {
    return type;
  }

  public TransactionHistoryRequest setType(Optional<Integer> type) {
    this.type = type;
    return this;
  }

  public Optional<CoingiAuthenticated.Pair> getCurrencyPair() {
    return currencyPair;
  }

  public TransactionHistoryRequest setCurrencyPair(Optional<CurrencyPair> currencyPair) {
    if (currencyPair != null && currencyPair.isPresent())
      this.currencyPair = Optional.of(new CoingiAuthenticated.Pair(currencyPair.get()));

    return this;
  }

  public Optional<Integer> getStatus() {
    return status;
  }

  public TransactionHistoryRequest setStatus(Optional<Integer> status) {
    this.status = status;
    return this;
  }
}
