package org.knowm.xchange.coinfloor.service;

import java.util.Collection;
import java.util.Collections;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class CoinfloorTradeHistoryParams implements TradeHistoryParamMultiCurrencyPair, TradeHistoryParamCurrencyPair, TradeHistoryParamsSorted,
    TradeHistoryParamOffset, TradeHistoryParamPaging {
  private Collection<CurrencyPair> pairs = Collections.emptySet();
  private CurrencyPair pair = null;
  private Order order = null;
  private Long offset = null;
  private Integer pageLength = null;

  @Override
  public void setCurrencyPairs(Collection<CurrencyPair> value) {
    pairs = value;
  }

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    return pairs;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair value) {
    pair = value;
  }

  @Override
  public Order getOrder() {
    return order;
  }

  @Override
  public void setOrder(Order order) {
    this.order = order;
  }

  @Override
  public Long getOffset() {
    return offset;
  }

  @Override
  public void setOffset(Long offset) {
    this.offset = offset;
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
  public void setPageNumber(Integer pageNumber) {
    if (pageNumber == null) {
      setOffset(null);
    } else if (pageLength != null) {
      this.offset = Long.valueOf(pageNumber * pageLength);
    }
  }

  @Override
  public Integer getPageNumber() {
    return (offset == null || pageLength == null) ? null : offset.intValue() / pageLength;
  }
}
