package org.knowm.xchange.wex.v3.service.trade.params;

import java.util.Date;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.wex.v3.WexAuthenticated;

/**
 * Transaction History paging params which combine id and time parameters and sort order.
 *
 * @author Peter N. Steinmetz Date: 4/3/15 Time: 8:29 AM
 */
public class WexTransHistoryParams extends DefaultTradeHistoryParamPaging
    implements TradeHistoryParamsIdSpan, TradeHistoryParamsTimeSpan {
  private WexAuthenticated.SortOrder sortOrder;
  private String startId;
  private String endId;
  private Date startTime;
  private Date endTime;

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
    return endId;
  }

  @Override
  public void setEndId(String endId) {
    this.endId = endId;
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
  public Date getEndTime() {
    return endTime;
  }

  @Override
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public WexAuthenticated.SortOrder getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(WexAuthenticated.SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }
}
