package org.knowm.xchange.cryptofacilities.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

// {"order":"cancel","cliOrdId":"my_client_id"}
/** https://www.cryptofacilities.com/resources/hc/en-us/articles/360000547454-Batch-Order */
@JsonInclude(Include.NON_NULL)
public class OrderCancellation implements OrderCommand {
  /** Always cancel */
  public final String order = "cancel";
  /** The unique identifier of the order to be cancelled */
  @JsonProperty("order_id")
  public final String orderId;
  /** Optional. The unique client identifier of the order to be cancelled. */
  public final String cliOrdId;

  public OrderCancellation(String orderId, String cliOrdId) {
    this.orderId = orderId;
    this.cliOrdId = cliOrdId;
  }
}
