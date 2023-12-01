package org.knowm.xchange.binance.dto.trade;

import java.util.Date;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.*;

public class BinanceTradeHistoryParams
    implements TradeHistoryParamInstrument,
        TradeHistoryParamLimit,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamsTimeSpan {

  /** mandatory */
  private Instrument instrument;

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

  public BinanceTradeHistoryParams(Instrument instrument) {
    this.instrument = instrument;
  }

  public BinanceTradeHistoryParams() {}

  public Instrument getInstrument() {
    return instrument;
  }

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
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
