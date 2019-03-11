package org.knowm.xchange.independentreserve.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Author: Kamil Zbikowski Date: 4/16/15 */
public class IndependentReserveTradeHistoryResponse {
  private final List<IndependentReserveTrade> independentReserveTrades;
  private final int pageSize;
  private final long totalItems;
  private final int totalPages;

  public IndependentReserveTradeHistoryResponse(
      @JsonProperty("Data") List<IndependentReserveTrade> independentReserveTrades,
      @JsonProperty("PageSize") int pageSize,
      @JsonProperty("TotalItems") long totalItems,
      @JsonProperty("TotalPages") int totalPages) {
    this.independentReserveTrades = independentReserveTrades;
    this.pageSize = pageSize;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
  }

  public List<IndependentReserveTrade> getIndependentReserveTrades() {
    return independentReserveTrades;
  }

  public int getPageSize() {
    return pageSize;
  }

  public long getTotalItems() {
    return totalItems;
  }

  public int getTotalPages() {
    return totalPages;
  }
}
