package org.knowm.xchange.independentreserve.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Kamil Zbikowski Date: 4/15/15
 */
public class IndependentReserveOpenOrdersResponse {
  private final String pageSize;
  private final String totalItems;
  private final String totalPages;
  private final List<IndependentReserveOpenOrder> independentReserveOrders;

  public IndependentReserveOpenOrdersResponse(@JsonProperty("PageSize") String pageSize, @JsonProperty("TotalItems") String totalItems,
      @JsonProperty("TotalPages") String totalPages, @JsonProperty("Data") List<IndependentReserveOpenOrder> independentReserveOrders) {
    this.independentReserveOrders = independentReserveOrders;
    this.pageSize = pageSize;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
  }

  public List<IndependentReserveOpenOrder> getIndependentReserveOrders() {
    return independentReserveOrders;
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
