package org.knowm.xchange.dsx.service.trade.params;

import java.util.Date;

import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

/**
 * @author Mikhail Wall
 */

public class DSXTransHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamsIdSpan, TradeHistoryParamsTimeSpan {

  private DSXAuthenticatedV2.SortOrder sortOrder;
  private DSXTransHistoryResult.Status status;
  private DSXTransHistoryResult.Type type;
  private String startId;
  private String endId;
  private Date startTime;
  private Date endTime;

  @Override
  public void setStartId(String startId) {

    this.startId = startId;
  }

  @Override
  public String getStartId() {

    return startId;
  }

  @Override
  public void setEndId(String endId) {

    this.endId = endId;
  }

  @Override
  public String getEndId() {

    return endId;
  }

  @Override
  public void setStartTime(Date startTime) {

    this.startTime = startTime;
  }

  @Override
  public Date getStartTime() {

    return startTime;
  }

  @Override
  public void setEndTime(Date endTime) {

    this.endTime = endTime;
  }

  @Override
  public Date getEndTime() {

    return endTime;
  }

  public void setSortOrder(DSXAuthenticatedV2.SortOrder sortOrder) {

    this.sortOrder = sortOrder;
  }

  public DSXAuthenticatedV2.SortOrder getSortOrder() {

    return sortOrder;
  }

  public DSXTransHistoryResult.Status getStatus() {
    return status;
  }

  public void setStatus(DSXTransHistoryResult.Status status) {
    this.status = status;
  }

  public DSXTransHistoryResult.Type getType() {
    return type;
  }

  public void setType(DSXTransHistoryResult.Type type) {
    this.type = type;
  }
}
