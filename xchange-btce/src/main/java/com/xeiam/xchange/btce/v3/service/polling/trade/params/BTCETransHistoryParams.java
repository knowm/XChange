package com.xeiam.xchange.btce.v3.service.polling.trade.params;

import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

import java.util.Date;

/**
 * Transaction History paging params which combine id and time parameters and sort order.
 * @author Peter N. Steinmetz
 *         Date: 4/3/15
 *         Time: 8:29 AM
 */
public class BTCETransHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamsIdSpan, TradeHistoryParamsTimeSpan {
  private BTCEAuthenticated.SortOrder sortOrder;
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

  public void setSortOrder(BTCEAuthenticated.SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }

  public BTCEAuthenticated.SortOrder getSortOrder() {
    return sortOrder;
  }
}
