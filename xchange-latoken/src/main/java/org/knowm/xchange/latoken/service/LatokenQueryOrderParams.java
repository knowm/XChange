package org.knowm.xchange.latoken.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderStatus;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;

public class LatokenQueryOrderParams implements OrderQueryParamCurrencyPair {

  private CurrencyPair currencyPair;
  private LatokenOrderStatus status;
  private Integer limit;

  public LatokenQueryOrderParams(
      CurrencyPair currencyPair, LatokenOrderStatus status, Integer limit) {
    this.currencyPair = currencyPair;
    this.status = status;
    this.limit = limit;
  }

  @Override
  public String getOrderId() {
    return null;
  }

  @Override
  public void setOrderId(String orderId) {}

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public LatokenOrderStatus getStatus() {
    return status;
  }

  public void setStatus(LatokenOrderStatus status) {
    this.status = status;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
