package org.knowm.xchange.service.trade.params;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Generic {@link TradeHistoryParams} implementation that implements all the interfaces in the
 * hierarchy and can be safely (without getting exceptions, if that all the required fields are
 * non-null) passed to any implementation of {@link
 * TradeService#getTradeHistory(TradeHistoryParams)} .
 */
public class TradeHistoryParamsAll
    implements TradeHistoryParamsTimeSpan,
        TradeHistoryParamPaging,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamOffset,
        TradeHistoryParamCurrencyPair,
        TradeHistoryParamMultiCurrencyPair,
        TradeHistoryParamInstrument,
        TradeHistoryParamMultiInstrument,
        TradeHistoryParamLimit {

  private Integer pageLength;
  private Integer pageNumber;
  private String startId;
  private String endId;
  private Date startTime;
  private Date endTime;
  private Long offset;
  private CurrencyPair pair;
  private Collection<CurrencyPair> pairs = Collections.emptySet();
  private Instrument instrument;
  private Collection<Instrument> instruments = Collections.emptySet();
  private Integer limit;

  @Override
  public Integer getPageLength() {

    return pageLength;
  }

  @Override
  public void setPageLength(Integer count) {

    this.pageLength = count;
  }

  @Override
  public String getStartId() {

    return startId;
  }

  @Override
  public void setStartId(String from) {

    startId = from;
  }

  @Override
  public String getEndId() {

    return endId;
  }

  @Override
  public void setEndId(String endId) {

    this.endId = endId;
  }

  @Override
  public Date getEndTime() {

    return endTime;
  }

  @Override
  public void setEndTime(Date to) {

    endTime = to;
  }

  @Override
  public Date getStartTime() {

    return startTime;
  }

  @Override
  public void setStartTime(Date startTime) {

    this.startTime = startTime;
  }

  @Override
  public Long getOffset() {

    if (offset != null || pageLength == null || pageNumber == null) return offset;
    else return (long) pageLength * pageNumber;
  }

  @Override
  public void setOffset(Long offset) {

    this.offset = offset;
  }

  @Override
  public Integer getPageNumber() {

    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {

    this.pageNumber = pageNumber;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    if (pair == null && instrument instanceof CurrencyPair) {
      return (CurrencyPair) instrument;
    }
    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    if (pairs.isEmpty() && !instruments.isEmpty()) {
      return instruments.stream()
          .filter(instrument -> instrument instanceof CurrencyPair)
          .map(instrument -> (CurrencyPair) instrument)
          .collect(Collectors.toSet());
    }
    return pairs;
  }

  @Override
  public void setCurrencyPairs(Collection<CurrencyPair> value) {

    pairs = value;
  }

  @Override
  public Instrument getInstrument() {
    if (instrument == null && pair != null) {
      return pair;
    }

    return instrument;
  }

  @Override
  public void setInstrument(final Instrument instrument) {
    this.instrument = instrument;
  }

  @Override
  public Collection<Instrument> getInstruments() {
    if (instruments.isEmpty() && !pairs.isEmpty()) {
      return pairs.stream().map(pair -> (Instrument) pair).collect(Collectors.toList());
    }

    return instruments;
  }

  @Override
  public void setInstruments(final Collection<Instrument> instruments) {
    this.instruments = instruments;
  }

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
