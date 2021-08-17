package org.knowm.xchange.bitstamp.service;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class BitstampTradeHistoryParams
    implements TradeHistoryParamCurrencyPair,
        TradeHistoryParamsSorted,
        TradeHistoryParamOffset,
        TradeHistoryParamPaging,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamsIdSpan {
  private CurrencyPair currencyPair;
  private Order order;
  private Integer offset;
  private Integer pageLength;
  private Date startTime;
  private String startId;

  public BitstampTradeHistoryParams(CurrencyPair currencyPair, Integer pageLength) {
    this.currencyPair = currencyPair;
    this.pageLength = pageLength;
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

  @Override
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
  public Date getStartTime() {
    return startTime;
  }

  /**
   * This will fetch historic user trades with a timestamp greater than or equal to startTime.
   *
   * @param startTime a start time with seconds precision. Milliseconds will be truncated.
   */
  @Override
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Override
  public Date getEndTime() {
    return null;
  }

  @Override
  public void setEndTime(Date endTime) {
    throw new UnsupportedOperationException("Bitstamp doesn't support end time");
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    if (pageNumber == null) {
      setOffset(null);
    } else if (pageLength != null) {
      this.offset = pageNumber * pageLength;
    }
  }

  @Override
  public String getStartId() {
    return startId;
  }

  @Override
  public void setStartId(String startId) {
    this.startId = startId;
  }

  @Override
  public String getEndId() {
    return null;
  }

  @Override
  public void setEndId(String endId) {
    throw new UnsupportedOperationException("Bitstamp doesn't support end id.");
  }
}
