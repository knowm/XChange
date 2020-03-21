package org.knowm.xchange.latoken.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderStatus;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;

public class LatokenQueryOrderParams implements OrderQueryParamInstrument {

  private Instrument currencyPair;
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
  public Instrument getInstrument() {
    return currencyPair;
  }

  @Override
  public void setInstrument(Instrument currencyPair) {
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
