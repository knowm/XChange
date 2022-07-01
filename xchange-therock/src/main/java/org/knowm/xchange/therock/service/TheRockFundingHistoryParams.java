package org.knowm.xchange.therock.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;

public class TheRockFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
    implements TradeHistoryParamCurrency, HistoryParamsFundingType {

  private Currency currency;
  private FundingRecord.Type type;

  public TheRockFundingHistoryParams() {}

  @Override
  public FundingRecord.Type getType() {
    return type;
  }

  @Override
  public void setType(FundingRecord.Type type) {
    this.type = type;
  }

  @Override
  public Currency getCurrency() {
    return currency;
  }

  @Override
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
