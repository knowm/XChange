package org.knowm.xchange.huobi.service;

import java.util.Date;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;

public class HuobiFundingHistoryParams implements 
  TradeHistoryParamCurrency, 
  HistoryParamsFundingType,
  TradeHistoryParamsIdSpan
  {

  private String from;
  private Currency currency;
  private Type type;
  
  public HuobiFundingHistoryParams(final String from, final Currency currency, Type type) {
	this.from = from;
    this.currency = currency;
    this.type = type;
  }

  @Override
  public Currency getCurrency() {
    return this.currency;
  }

  @Override
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public Type getType() {
	return this.type;
  }

  @Override
  public void setType(Type type) {
	  this.type = type;
  }

  @Override
  public String getStartId() {
	return this.from;
  }

  @Override
  public void setStartId(String startId) {
	this.from = startId;
  }

  @Override
  public String getEndId() {
	return null;
  }

  @Override
  public void setEndId(String endId) {
	
  }
}