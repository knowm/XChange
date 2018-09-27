package org.knowm.xchange.bx.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BxTradeHistoryParams implements TradeHistoryParams {

  private String startDate;
  private String endDate;

  public BxTradeHistoryParams(String startDate, String endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "BxTradeHistoryParams{" + "startDate=" + startDate + ", endDate=" + endDate + '}';
  }
}
