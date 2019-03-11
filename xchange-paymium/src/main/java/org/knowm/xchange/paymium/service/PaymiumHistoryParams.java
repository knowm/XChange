package org.knowm.xchange.paymium.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;

public class PaymiumHistoryParams implements TradeHistoryParamOffset, TradeHistoryParamLimit {

  private Long offset;

  private Integer limit;

  public PaymiumHistoryParams() {
    this.offset = 0L;
    this.limit = 20;
  }

  public PaymiumHistoryParams(Long offset, Integer limit) {
    this.offset = offset;
    this.limit = limit;
  }

  @Override
  public Long getOffset() {
    return offset;
  }

  @Override
  public void setOffset(final Long offset) {
    this.offset = offset;
  }

  @Override
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
