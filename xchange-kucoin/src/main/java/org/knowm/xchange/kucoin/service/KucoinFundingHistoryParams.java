package org.knowm.xchange.kucoin.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

public class KucoinFundingHistoryParams
    implements TradeHistoryParamPaging, TradeHistoryParamCurrency, HistoryParamsFundingType {

  private Integer pageLength;
  private Integer pageNumber;
  private Currency currency;
  private FundingRecord.Type type;

  @Override
  public Integer getPageLength() {
    return pageLength;
  }

  @Override
  public void setPageLength(Integer pageLength) {

    this.pageLength = pageLength;
  }

  @Override
  public Integer getPageNumber() {

    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {

    this.pageNumber = pageNumber;
  }

  @Override
  public Type getType() {

    return type;
  }

  @Override
  public void setType(Type type) {

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
