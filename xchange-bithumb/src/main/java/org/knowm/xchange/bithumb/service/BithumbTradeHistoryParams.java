package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

public class BithumbTradeHistoryParams
    implements TradeHistoryParamInstrument, TradeHistoryParamPaging {

  private Integer pageLength;
  private Integer pageNumber;
  private Instrument currencyPair;

  @Override
  public Integer getPageLength() {
    return this.pageLength;
  }

  @Override
  public void setPageLength(Integer pageLength) {
    this.pageLength = pageLength;
  }

  @Override
  public Integer getPageNumber() {
    return this.pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  @Override
  public Instrument getInstrument() {
    return currencyPair;
  }

  @Override
  public void setInstrument(Instrument pair) {
    this.currencyPair = pair;
  }
}
