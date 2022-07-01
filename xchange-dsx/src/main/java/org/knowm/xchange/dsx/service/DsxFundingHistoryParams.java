package org.knowm.xchange.dsx.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;

public class DsxFundingHistoryParams
    implements TradeHistoryParamCurrency, TradeHistoryParamOffset, TradeHistoryParamLimit {

  private Currency currency;
  private Long offset;
  private Integer limit;

  public DsxFundingHistoryParams() {}

  private DsxFundingHistoryParams(Currency currency, Long offset, Integer limit) {
    this.currency = currency;
    this.offset = offset;
    this.limit = limit;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public Currency getCurrency() {
    return currency;
  }

  @Override
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public Long getOffset() {
    return offset;
  }

  @Override
  public void setOffset(Long offset) {
    this.offset = offset;
  }

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public static class Builder {

    private Currency currency;

    private Long offset;

    private Integer limit;

    public Builder currency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public Builder offset(Long offset) {
      this.offset = offset;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public DsxFundingHistoryParams build() {
      return new DsxFundingHistoryParams(currency, offset, limit);
    }
  }
}
