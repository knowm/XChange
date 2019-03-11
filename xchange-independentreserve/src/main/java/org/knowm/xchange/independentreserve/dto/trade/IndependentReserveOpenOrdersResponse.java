package org.knowm.xchange.independentreserve.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Author: Kamil Zbikowski Date: 4/15/15 */
public class IndependentReserveOpenOrdersResponse {
  private final int pageSize;
  private final long totalItems;
  private final int totalPages;
  private final List<IndependentReserveOpenOrder> independentReserveOrders;

  public IndependentReserveOpenOrdersResponse(
      @JsonProperty("PageSize") int pageSize,
      @JsonProperty("TotalItems") long totalItems,
      @JsonProperty("TotalPages") int totalPages,
      @JsonProperty("Data") List<IndependentReserveOpenOrder> independentReserveOrders) {
    this.independentReserveOrders = independentReserveOrders;
    this.pageSize = pageSize;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
  }

  public List<IndependentReserveOpenOrder> getIndependentReserveOrders() {
    return independentReserveOrders;
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
