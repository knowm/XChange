package org.knowm.xchange.latoken.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;

public class LatokenTradeHistoryParams
    implements TradeHistoryParamInstrument, TradeHistoryParamLimit {

  private Instrument currencyPair;

  /** Optional parameter. Defines the maximal number of trades in the response. */
  private Integer limit;

  public LatokenTradeHistoryParams() {}

  public LatokenTradeHistoryParams(CurrencyPair currencyPair, Integer limit) {
    this.currencyPair = currencyPair;
    this.limit = limit;
  }

  public LatokenTradeHistoryParams(CurrencyPair currencyPair) {
    this(currencyPair, null);
  }

  @Override
  public Instrument getInstrument() {
    return currencyPair;
  }

  @Override
  public void setInstrument(Instrument currencyPair) {
    this.currencyPair = currencyPair;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
