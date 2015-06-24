package com.xeiam.xchange.ripple.service.polling.params;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsAll;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

public class RippleTradeHistoryParams implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging, TradeHistoryParamsTimeSpan {

  public static final int DEFAULT_PAGE_LENGTH = 20;

  public static final int DEFAULT_TRADE_COUNT_LIMIT = 10;

  public static final int DEFAULT_API_CALL_COUNT = 100;

  private final TradeHistoryParamsAll all = new TradeHistoryParamsAll();

  private String account = null;
  private String hashLimit = null;

  private int tradeCount = 0;
  private int tradeCountLimit = DEFAULT_TRADE_COUNT_LIMIT;

  private int apiCallCount = 0;
  private int apiCallCountLimit = DEFAULT_API_CALL_COUNT;

  private Collection<String> preferredBaseCurrency = new HashSet<String>();
  private Collection<String> preferredCounterCurrency = new HashSet<String>();

  public RippleTradeHistoryParams() {
    setPageLength(DEFAULT_PAGE_LENGTH);
  }

  public void setAccount(final String value) {
    account = value;
  }

  public String getAccount() {
    return account;
  }

  public void setHashLimit(final String value) {
    hashLimit = value;
  }

  public String getHashLimit() {
    return hashLimit;
  }

  public void resetApiCallCount() {
    apiCallCount = 0;
  }

  public void incrementApiCallCount() {
    apiCallCount++;
  }

  public int getApiCallCount() {
    return apiCallCount;
  }

  public void setApiCallCountLimit(final int value) {
    apiCallCountLimit = value;
  }

  public int getApiCallCountLimit() {
    return apiCallCountLimit;
  }

  public void resetTradeCount() {
    tradeCount = 0;
  }

  public void incrementTradeCount() {
    tradeCount++;
  }

  public int getTradeCount() {
    return tradeCount;
  }

  public void setTradeCountLimit(final int value) {
    tradeCountLimit = value;
  }

  public int getTradeCountLimit() {
    return tradeCountLimit;
  }

  public void addPreferredBaseCurrency(final String value) {
    preferredBaseCurrency.add(value);
  }

  public Collection<String> getPreferredBaseCurrency() {
    return preferredBaseCurrency;
  }

  public void addPreferredCounterCurrency(final String value) {
    preferredCounterCurrency.add(value);
  }

  public Collection<String> getPreferredCounterCurrency() {
    return preferredCounterCurrency;
  }

  @Override
  public void setCurrencyPair(final CurrencyPair value) {
    all.setCurrencyPair(value);
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return all.getCurrencyPair();
  }

  @Override
  public void setPageLength(final Integer value) {
    all.setPageLength(value);
  }

  /**
   * @return the number of notifications to return in a single query, if not set the server assumes a default of 10.
   */
  @Override
  public Integer getPageLength() {
    return all.getPageLength();
  }

  @Override
  public void setPageNumber(final Integer value) {
    all.setPageNumber(value);
  }

  @Override
  public Integer getPageNumber() {
    return all.getPageNumber();
  }

  @Override
  public void setStartTime(final Date value) {
    all.setStartTime(value);
  }

  @Override
  public Date getStartTime() {
    return all.getStartTime();
  }

  @Override
  public void setEndTime(final Date value) {
    all.setEndTime(value);
  }

  @Override
  public Date getEndTime() {
    return all.getEndTime();
  }
}
