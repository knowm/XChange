package org.knowm.xchange.coinsuper.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class CoinsuperTradeHistoryParams
    implements TradeHistoryParamInstrument,
        TradeHistoryParamsSorted,
        TradeHistoryParamOffset,
        TradeHistoryParamPaging {
  private Instrument currencyPair;
  private Order order;
  private Integer offset;
  private Integer pageLength;
  private String orderNoList;

  public CoinsuperTradeHistoryParams(CurrencyPair currencyPair, Integer pageLength) {
    this.currencyPair = currencyPair;
    this.pageLength = pageLength;
  }

  @Override
  public Instrument getInstrument() {
    return currencyPair;
  }

  @Override
  public void setInstrument(Instrument currencyPair) {
    this.currencyPair = currencyPair;
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
    return offset == null ? null : Long.valueOf(offset);
  }

  public void setOffset(Long offset) {
    this.offset = offset == null ? null : offset.intValue();
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
    return (offset == null || pageLength == null) ? null : offset / pageLength;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    if (pageNumber == null) {
      setOffset(null);
    } else if (pageLength != null) {
      this.offset = pageNumber * pageLength;
    }
  }

  public String getOrderNoList() {
    return orderNoList;
  }

  public void setOrderNoList(String orderNoList) {
    this.orderNoList = orderNoList;
  }
}
