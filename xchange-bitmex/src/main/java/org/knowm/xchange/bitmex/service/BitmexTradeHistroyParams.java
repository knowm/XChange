package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;

public class BitmexTradeHistroyParams
    implements TradeHistoryParamCurrency, TradeHistoryParamLimit, TradeHistoryParamOffset {
  private Currency currency;
  private Integer limit;
  private Long offset;

  @Override
  public Currency getCurrency() {
    return this.currency;
  }

  @Override
  public void setCurrency(final Currency currency) {
    this.currency = currency;
  }

  @Override
  public Integer getLimit() {
    return this.limit;
  }

  @Override
  public void setLimit(final Integer limit) {
    this.limit = limit;
  }

  @Override
  public Long getOffset() {
    return this.offset;
  }

  @Override
  public void setOffset(final Long offset) {
    this.offset = offset;
  }
}
