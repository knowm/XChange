package org.knowm.xchange.coinfloor.service;

import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamMultiInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class CoinfloorTradeHistoryParams
    implements TradeHistoryParamMultiInstrument,
        TradeHistoryParamInstrument,
        TradeHistoryParamsSorted,
        TradeHistoryParamOffset,
        TradeHistoryParamPaging {
  private Collection<Instrument> instruments = Collections.emptySet();
  private Instrument instrument = null;
  private Order order = null;
  private Long offset = null;
  private Integer pageLength = null;

  @Override
  public Collection<Instrument> getInstruments() {
    return instruments;
  }

  @Override
  public void setInstruments(Collection<Instrument> value) {
    instruments = value;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument value) {
    instrument = value;
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
  public Integer getPageNumber() {
    return (offset == null || pageLength == null) ? null : offset.intValue() / pageLength;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    if (pageNumber == null) {
      setOffset(null);
    } else if (pageLength != null) {
      this.offset = Long.valueOf(pageNumber * pageLength);
    }
  }
}
