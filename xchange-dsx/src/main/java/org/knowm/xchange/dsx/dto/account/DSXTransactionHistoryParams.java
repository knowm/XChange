package org.knowm.xchange.dsx.dto.account;

import java.util.Date;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class DSXTransactionHistoryParams implements TradeHistoryParamsTimeSpan, TradeHistoryParamsIdSpan, TradeHistoryParamCurrency {

  private Currency currency = null;
  private String startId = null;
  private String endId = null;
  private Date startTime = null;
  private Date endTime = null;

  @Override
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public Currency getCurrency() {
    return currency;
  }

  @Override
  public void setStartId(String startId) {
    this.startId = startId;
  }

  @Override
  public String getStartId() {
    return startId;
  }

  @Override
  public void setEndId(String endId) {
    this.endId = endId;
  }

  @Override
  public String getEndId() {
    return endId;
  }

  @Override
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Override
  public Date getStartTime() {
    return startTime;
  }

  @Override
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  @Override
  public Date getEndTime() {
    return endTime;
  }
}
