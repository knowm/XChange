package org.knowm.xchange.kucoin.service;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class KucoinTradeHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging, TradeHistoryParamsTimeSpan {

  private CurrencyPair currencyPair;
  private Integer pageLength;
  private Integer pageNumber;
  private Date startTime;
  private Date endTime;

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

  @Override
  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.currencyPair = pair;
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
