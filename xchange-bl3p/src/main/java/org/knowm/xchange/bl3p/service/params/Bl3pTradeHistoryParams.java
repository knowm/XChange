package org.knowm.xchange.bl3p.service.params;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

public class Bl3pTradeHistoryParams implements TradeHistoryParamCurrency, TradeHistoryParamPaging {

  public enum TransactionType {
    TRADE,
    FEE,
    DEPOSIT,
    WITHDRAW;

    @Override
    public String toString() {
      return super.toString().toLowerCase();
    }
  }

  private Currency currency;
  private int pageLength;
  private int pageNumber;
  private TransactionType type;

  public Bl3pTradeHistoryParams(Currency currency, TransactionType type) {
    this(currency, type, 1);
  }

  public Bl3pTradeHistoryParams(Currency currency, TransactionType type, int pageNumber) {
    this(currency, type, pageNumber, 50);
  }

  public Bl3pTradeHistoryParams(
      Currency currency, TransactionType type, int pageNumber, int pageLength) {
    this.currency = currency;
    this.type = type;
    this.pageNumber = pageNumber;
    this.pageLength = pageLength;
  }

  @Override
  public Currency getCurrency() {
    return currency;
  }

  @Override
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public Integer getPageLength() {
    return pageLength;
  }

  @Override
  public void setPageLength(Integer pageLength) {
    this.pageLength = pageLength;
  }

  @Override
  public Integer getPageNumber() {
    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }
}
