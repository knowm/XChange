package org.knowm.xchange.dragonex.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class DragonexTradeHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamCurrency,
        TradeHistoryParamPaging,
        HistoryParamsFundingType {

  private Currency currency;
  private Integer pageLength;
  private Integer pageNumber;
  private FundingRecord.Type type;

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
  public String toString() {
    return "DragonexTradeHistoryParams ["
        + (currency != null ? "currency=" + currency + ", " : "")
        + (pageLength != null ? "pageLength=" + pageLength + ", " : "")
        + (pageNumber != null ? "pageNumber=" + pageNumber + ", " : "")
        + (type != null ? "type=" + type : "")
        + "]";
  }
}
