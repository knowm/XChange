package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

import java.util.Date;

public class BitrueTradeHistoryParams
    implements TradeHistoryParamCurrencyPair,
        TradeHistoryParamLimit,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamsTimeSpan {

  /** mandatory */
  private CurrencyPair currencyPair;
  /** optional */
  private Integer limit;
  /** optional */
  private String startId;
  /** ignored */
  private String endId;
  /** optional */
  private Date startTime;
  /** optional */
  private Date endTime;

  public BitrueTradeHistoryParams(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public BitrueTradeHistoryParams() {}

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getStartId() {
    return startId;
  }

  public void setStartId(String startId) {
    this.startId = startId;
  }

  public String getEndId() {
    return endId;
  }

  public void setEndId(String endId) {
    this.endId = endId;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
