package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Response body for POST /v1/orders/search. */
public class DaseBatchGetOrdersResponse {

  private final List<DaseOrder> orders;

  @JsonCreator
  public DaseBatchGetOrdersResponse(@JsonProperty("orders") List<DaseOrder> orders) {
    this.orders = orders;
  }

  public List<DaseOrder> getOrders() {
    return orders;
  }
}
