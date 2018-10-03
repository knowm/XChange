package org.knowm.xchange.independentreserve.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Author: Kamil Zbikowski Date: 4/16/15 */
public class IndependentReserveTradeHistoryResponse {
  private final List<IndependentReserveTrade> independentReserveTrades;
  private final String pageSize;
  private final String totalItems;
  private final String totalPages;

  public IndependentReserveTradeHistoryResponse(
      @JsonProperty("Data") List<IndependentReserveTrade> independentReserveTrades,
      @JsonProperty("PageSize") String pageSize,
      @JsonProperty("TotalItems") String totalItems,
      @JsonProperty("TotalPages") String totalPages) {
    this.independentReserveTrades = independentReserveTrades;
    this.pageSize = pageSize;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
  }

  public List<IndependentReserveTrade> getIndependentReserveTrades() {
    return independentReserveTrades;
  }

  public String getPageSize() {
    return pageSize;
  }

  public String getTotalItems() {
    return totalItems;
  }

  public String getTotalPages() {
    return totalPages;
  }
}
