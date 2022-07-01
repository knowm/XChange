package org.knowm.xchange.deribit.v2.service;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class DeribitTradeHistoryParams
    implements TradeHistoryParamInstrument,
        TradeHistoryParamCurrencyPair,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamLimit,
        TradeHistoryParamsSorted,
        DeribitTradeHistoryParamsOld {

  /** mandatory if currency is not specified */
  private Instrument instrument;
  /** mandatory if instrument is not specified, ignored otherwise */
  private CurrencyPair currencyPair;
  /** optional */
  private Date startTime;
  /** optional */
  private Date endTime;
  /** optional, ignored if startTime and endTime are specified */
  private String startId;
  /** optional, ignored if startTime and endTime are specified */
  private String endId;
  /** optional */
  private Integer limit;
  /** optional */
  private Order order;
  /** optional */
  private Boolean includeOld;

  public DeribitTradeHistoryParams() {}

  public DeribitTradeHistoryParams(Instrument instrument) {
    this.instrument = instrument;
  }

  public DeribitTradeHistoryParams(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
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
    return endId;
  }

  @Override
  public void setEndId(String endId) {
    this.endId = endId;
  }

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public Order getOrder() {
    return order;
  }

  @Override
  public void setOrder(Order order) {
    this.order = order;
  }

  @Override
  public Boolean isIncludeOld() {
    return includeOld;
  }

  @Override
  public void setIncludeOld(Boolean includeOld) {
    this.includeOld = includeOld;
  }
}
