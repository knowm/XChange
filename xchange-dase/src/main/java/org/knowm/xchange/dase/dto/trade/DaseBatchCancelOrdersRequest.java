package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Request body for DELETE /v1/orders to batch cancel by IDs. */
public class DaseBatchCancelOrdersRequest {

  @JsonProperty("order_ids")
  public List<String> orderIds;
}
