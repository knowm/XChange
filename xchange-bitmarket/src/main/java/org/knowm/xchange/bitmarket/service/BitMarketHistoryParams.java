package org.knowm.xchange.bitmarket.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;

/** @author kfonal */
public class BitMarketHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamOffset {

  private CurrencyPair currencyPair;
  private Long offset;
  private Integer count;

  /** Default constructor */
  public BitMarketHistoryParams() {

    this.currencyPair = CurrencyPair.BTC_PLN;
    this.offset = 0L;
    this.count = 1000;
  }

  /**
   * Constructor
   *
   * @param currencyPair
   * @param offset
   * @param count
   */
  public BitMarketHistoryParams(CurrencyPair currencyPair, Long offset, Integer count) {

    this.currencyPair = currencyPair;
    this.offset = offset;
    this.count = count;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public Long getOffset() {
    return offset;
  }

  @Override
  public void setOffset(Long offset) {
    this.offset = offset;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
