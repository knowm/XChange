package org.knowm.xchange.coinbasepro.dto.account;

import java.util.Date;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamAccountId;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class CoinbaseProFundingHistoryParams implements
    TradeHistoryParams,
    HistoryParamsFundingType,
    TradeHistoryParamLimit, TradeHistoryParamsTimeSpan, TradeHistoryParamAccountId {

  private Type type;
  private Integer limit;
  private Date beforeDate;
  private Date afterDate;
  private String accountId;

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public void setType(Type type) {
    this.type = type;
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
  public Date getStartTime() {
    return beforeDate;
  }

  @Override
  public void setStartTime(Date startTime) {
    this.beforeDate = startTime;
  }

  @Override
  public Date getEndTime() {
    return afterDate;
  }

  @Override
  public void setEndTime(Date endTime) {
    this.afterDate = endTime;
  }

  @Override
  public String getAccountId() {
    return accountId;
  }

  @Override
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
}
