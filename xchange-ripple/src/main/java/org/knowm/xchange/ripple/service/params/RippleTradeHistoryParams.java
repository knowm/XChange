package org.knowm.xchange.ripple.service.params;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

/** The complete set of parameters that a Ripple trade history query will consider. */
public class RippleTradeHistoryParams
    implements TradeHistoryParamCurrencyPair,
        TradeHistoryParamPaging,
        TradeHistoryParamsTimeSpan,
        RippleTradeHistoryAccount,
        RippleTradeHistoryHashLimit,
        RippleTradeHistoryCount,
        RippleTradeHistoryPreferredCurrencies {

  public static final int DEFAULT_PAGE_LENGTH = 20;

  private final TradeHistoryParamsAll all = new TradeHistoryParamsAll();

  private String account = null;
  private String hashLimit = null;

  private int tradeCount = 0;
  private int tradeCountLimit = DEFAULT_TRADE_COUNT_LIMIT;

  private int apiCallCount = 0;
  private int apiCallCountLimit = DEFAULT_API_CALL_COUNT;

  private Collection<Currency> preferredBaseCurrency = new HashSet<>();
  private Collection<Currency> preferredCounterCurrency = new HashSet<>();

  public RippleTradeHistoryParams() {
    setPageLength(DEFAULT_PAGE_LENGTH);
  }

  @Override
  public String getAccount() {
    return account;
  }

  public void setAccount(final String value) {
    account = value;
  }

  @Override
  public String getHashLimit() {
    return hashLimit;
  }

  public void setHashLimit(final String value) {
    hashLimit = value;
  }

  @Override
  public void resetApiCallCount() {
    apiCallCount = 0;
  }

  @Override
  public void incrementApiCallCount() {
    apiCallCount++;
  }

  @Override
  public int getApiCallCount() {
    return apiCallCount;
  }

  @Override
  public int getApiCallCountLimit() {
    return apiCallCountLimit;
  }

  public void setApiCallCountLimit(final int value) {
    apiCallCountLimit = value;
  }

  @Override
  public void resetTradeCount() {
    tradeCount = 0;
  }

  @Override
  public void incrementTradeCount() {
    tradeCount++;
  }

  @Override
  public int getTradeCount() {
    return tradeCount;
  }

  @Override
  public int getTradeCountLimit() {
    return tradeCountLimit;
  }

  public void setTradeCountLimit(final int value) {
    tradeCountLimit = value;
  }

  public void addPreferredBaseCurrency(final Currency value) {
    preferredBaseCurrency.add(value);
  }

  @Override
  public Collection<Currency> getPreferredBaseCurrency() {
    return preferredBaseCurrency;
  }

  public void addPreferredCounterCurrency(final Currency value) {
    preferredCounterCurrency.add(value);
  }

  @Override
  public Collection<Currency> getPreferredCounterCurrency() {
    return preferredCounterCurrency;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return all.getCurrencyPair();
  }

  @Override
  public void setCurrencyPair(final CurrencyPair value) {
    all.setCurrencyPair(value);
  }

  /**
   * @return the number of notifications to return in a single query, if not set the server assumes
   *     a default of 10.
   */
  @Override
  public Integer getPageLength() {
    return all.getPageLength();
  }

  @Override
  public void setPageLength(final Integer value) {
    all.setPageLength(value);
  }

  @Override
  public Integer getPageNumber() {
    return all.getPageNumber();
  }

  @Override
  public void setPageNumber(final Integer value) {
    all.setPageNumber(value);
  }

  @Override
  public Date getStartTime() {
    return all.getStartTime();
  }

  @Override
  public void setStartTime(final Date value) {
    all.setStartTime(value);
  }

  @Override
  public Date getEndTime() {
    return all.getEndTime();
  }

  @Override
  public void setEndTime(final Date value) {
    all.setEndTime(value);
  }
}
