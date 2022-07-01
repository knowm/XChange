package org.knowm.xchange.huobi.service;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class HuobiTradeHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamsIdSpan, TradeHistoryParamsTimeSpan {
  private CurrencyPair currencyPair;
  private String startId;
  private Date startTime;
  private Date endTime;

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public String getStartId() {
    return startId;
  }

  @Override
  public void setStartId(String startId) {
    this.startId = startId;
  }

  @Override
  public String getEndId() {
    return null;
  }

  @Override
  public void setEndId(String endId) {
    throw new NotAvailableFromExchangeException("Huobi supports only 'from' ID.");
  }

  @Override
  public Date getStartTime() {
    return startTime;
  }

  @Override
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Override
  public Date getEndTime() {
    return endTime;
  }

  @Override
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
