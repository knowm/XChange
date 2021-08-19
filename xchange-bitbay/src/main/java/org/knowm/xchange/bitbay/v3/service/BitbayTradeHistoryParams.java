package org.knowm.xchange.bitbay.v3.service;

import java.util.ArrayList;
import java.util.Collection;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;

/** @author walec51 */
public class BitbayTradeHistoryParams
    implements TradeHistoryParamLimit,
        TradeHistoryParamNextPageCursor,
        TradeHistoryParamMultiCurrencyPair {

  public static final String START = "start"; // special value that enables paging

  /**
   * In order to get a cursor that enables paging you need initially set this to the value 'start'
   */
  private String nextPageCursor;

  private Integer limit;

  private Collection<CurrencyPair> currencyPairs = new ArrayList<>();

  public BitbayTradeHistoryParams() {
    this(START, 300);
  }

  public BitbayTradeHistoryParams(String nextPageCursor, Integer limit) {
    this.nextPageCursor = nextPageCursor;
    this.limit = limit;
  }

  /** Use this factory method to enable paging. */
  public static BitbayTradeHistoryParams startBitBayTradeHistoryParams(Integer limit) {
    return new BitbayTradeHistoryParams(START, limit);
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
  public String getNextPageCursor() {
    return nextPageCursor;
  }

  @Override
  public void setNextPageCursor(String nextPageCursor) {
    this.nextPageCursor = nextPageCursor;
  }

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    return currencyPairs;
  }

  @Override
  public void setCurrencyPairs(Collection<CurrencyPair> pairs) {
    this.currencyPairs = pairs;
  }
}
