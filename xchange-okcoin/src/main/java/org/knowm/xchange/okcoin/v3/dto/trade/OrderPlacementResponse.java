package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderPlacementResponse extends OkexResponse {

  /** order ID */
  @JsonProperty("order_id")
  private String orderId;

  /** the order ID customised by yourself */
  @JsonProperty("client_oid")
  private String clientOid;
}
