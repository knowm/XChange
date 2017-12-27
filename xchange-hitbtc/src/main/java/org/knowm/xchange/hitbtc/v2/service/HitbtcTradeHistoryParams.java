package org.knowm.xchange.hitbtc.v2.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class HitbtcTradeHistoryParams implements TradeHistoryParams {

  private HitbtcTradeHistoryParams(Currency currency, Integer offset, Integer limit) {
    this.currency = currency;
    this.offset = offset;
    this.limit = limit;
  }

  private Currency currency;

  private Integer offset;

  private Integer limit;

  public Currency getCurrency() {
    return currency;
  }

  public Integer getOffset() {
    return offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public static Builder builder(){
    return new Builder();
  }

  public static class Builder {

    private Currency currency;

    private Integer offset;

    private Integer limit;

    public Builder currency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public Builder offset(Integer offset) {
      this.offset = offset;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public HitbtcTradeHistoryParams build() {
      return new HitbtcTradeHistoryParams(currency, offset, limit);
    }
  }

}
