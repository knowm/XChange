package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Wrapper for GET /v1/orders list response. */
public class DaseOrdersListResponse {

  private final List<DaseOrder> orders;

  @JsonCreator
  public DaseOrdersListResponse(@JsonProperty("orders") List<DaseOrder> orders) {
    this.orders = orders;
  }

  public List<DaseOrder> getOrders() {
    return orders;
  }
}
