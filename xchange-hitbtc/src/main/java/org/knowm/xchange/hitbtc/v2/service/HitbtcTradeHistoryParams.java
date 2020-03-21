package org.knowm.xchange.hitbtc.v2.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;

public class HitbtcTradeHistoryParams
    implements TradeHistoryParamLimit, TradeHistoryParamOffset, TradeHistoryParamInstrument {

  private Instrument pair;
  private Integer limit;
  private Long offset;

  public HitbtcTradeHistoryParams(CurrencyPair pair, Integer limit, Long offset) {
    this.pair = pair;
    this.limit = limit;
    this.offset = offset;
  }

  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public void setInstrument(Instrument pair) {
    this.pair = pair;
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
  public Long getOffset() {
    return offset;
  }

  @Override
  public void setOffset(Long offset) {
    this.offset = offset;
  }
}
