package org.knowm.xchange.independentreserve.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class IndependentReserveTransactionsResponse {
  private final List<IndependentReserveTransaction> independentReserveTransactions;
  private final int pageSize;
  private final long totalItems;
  private final int totalPages;

  public IndependentReserveTransactionsResponse(
      @JsonProperty("Data") List<IndependentReserveTransaction> independentReserveTransactions,
      @JsonProperty("PageSize") int pageSize,
      @JsonProperty("TotalItems") long totalItems,
      @JsonProperty("TotalPages") int totalPages) {
    this.independentReserveTransactions = independentReserveTransactions;
    this.pageSize = pageSize;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
  }

  public List<IndependentReserveTransaction> getIndependentReserveTranasactions() {
    return independentReserveTransactions;
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
