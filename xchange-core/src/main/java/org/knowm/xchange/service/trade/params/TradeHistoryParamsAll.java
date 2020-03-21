package org.knowm.xchange.service.trade.params;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
  public Instrument getInstrument() {

    return instrument;
  }

  @Override
  public void setInstrument(Instrument pair) {

    this.instrument = pair;
  }

  @Override
  public Collection<Instrument> getInstruments() {

    return instruments;
  }

  @Override
  public void setInstruments(Collection<Instrument> value) {

    instruments = value;
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
