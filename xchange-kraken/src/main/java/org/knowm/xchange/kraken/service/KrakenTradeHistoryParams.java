package org.knowm.xchange.kraken.service;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class KrakenTradeHistoryParams
    implements TradeHistoryParamOffset,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamCurrencyPair {

  private Long offset;
  private String startId;
  private String endId;

  private Date endTime;
  private Date startTime;

  private CurrencyPair pair;

  @Override
  public Long getOffset() {
    return offset;
  }

  @Override
  public void setOffset(Long offset) {
    this.offset = offset;
  }

  @Override
  public String getStartId() {
    return startId;
  }

  @Override
  public String getEndId() {
    return endId;
  }

  @Override
  public void setStartId(String startId) {
    this.startId = startId;
  }

  @Override
  public void setEndId(String endId) {
    this.endId = endId;
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
  public Date getStartTime() {

    return startTime;
  }

  @Override
  public void setStartTime(Date time) {

    startTime = time;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }
}
