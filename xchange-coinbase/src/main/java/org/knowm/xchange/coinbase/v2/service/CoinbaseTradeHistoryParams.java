package org.knowm.xchange.coinbase.v2.service;

import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;

import java.time.Instant;

public class CoinbaseTradeHistoryParams
    implements TradeHistoryParamsIdSpan, TradeHistoryParamLimit {

  private String startId;
  private Integer limit;
  private Instant startDatetime;
  private Instant endDateTime;
  private String cursor;

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
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
    throw new NotAvailableFromExchangeException("Coinbase does not support ending transaction ID.");
  }

  @Override
  public void setEndId(String endId) {
    throw new NotAvailableFromExchangeException("Coinbase does not support ending transaction ID.");
  }

  public Instant getStartDatetime() {
    return startDatetime;
  }

  public void setStartDatetime(Instant startDatetime) {
    this.startDatetime = startDatetime;
  }

  public Instant getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(Instant endDateTime) {
    this.endDateTime = endDateTime;
  }

  public String getCursor() {
    return cursor;
  }

  public void setCursor(String cursor) {
    this.cursor = cursor;
  }
}
