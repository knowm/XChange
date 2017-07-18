package org.knowm.xchange.jubi.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

import java.util.Date;

/**
 * Created by Dzf on 2017/7/18.
 */
public class JubiTradeHistoryParams implements TradeHistoryParamCurrencyPair, TradeHistoryParamsTimeSpan {
  private CurrencyPair currencyPair;
  private Date startTime;

  public JubiTradeHistoryParams(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
    this.startTime = null;
  }

  public JubiTradeHistoryParams(CurrencyPair currencyPair, Date startTime) {
    this(currencyPair);
    this.startTime = startTime;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
    this.currencyPair =  currencyPair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Override
  public Date getStartTime() {
    return startTime;
  }

  @Override
  public void setEndTime(Date endTime) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Date getEndTime() {
    throw new NotAvailableFromExchangeException();
  }
}
