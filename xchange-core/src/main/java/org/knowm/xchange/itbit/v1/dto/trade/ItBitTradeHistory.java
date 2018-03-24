package org.knowm.xchange.itbit.v1.dto.trade;

import java.util.List;

public class ItBitTradeHistory {
  private Integer totalNumberOfRecords;
  private Integer currentPageNumber;
  private String latestExecutionId;
  private Integer recordsPerPage;
  private List<ItBitUserTrade> tradingHistory;

  public Integer getTotalNumberOfRecords() {
    return totalNumberOfRecords;
  }

  public Integer getCurrentPageNumber() {
    return currentPageNumber;
  }

  public String getLatestExecutionId() {
    return latestExecutionId;
  }

  public Integer getRecordsPerPage() {
    return recordsPerPage;
  }

  public List<ItBitUserTrade> getTradingHistory() {
    return tradingHistory;
  }
}
