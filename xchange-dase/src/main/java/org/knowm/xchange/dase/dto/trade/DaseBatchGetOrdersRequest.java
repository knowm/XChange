package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Request body for POST /v1/orders/search. */
public class DaseBatchGetOrdersRequest {

  @JsonProperty("order_ids")
  public List<String> orderIds;
}
