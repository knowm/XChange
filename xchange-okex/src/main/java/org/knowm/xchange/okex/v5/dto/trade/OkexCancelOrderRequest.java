package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 10-06-2021 */
@Builder
public class OkexCancelOrderRequest {
  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("ordId")
  private String orderId;

  @JsonProperty("clOrderId")
  private String clientOrderId;
}
