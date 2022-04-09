package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 10-06-2021 */
@Builder
public class OkexAmendOrderRequest {
  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("cxlOnFail")
  private boolean cancelOnFail;

  @JsonProperty("ordId")
  private String orderId;

  @JsonProperty("clOrdId")
  private String clientOrderId;

  @JsonProperty("reqId")
  private String requestId;

  @JsonProperty("newSz")
  private String amendedAmount;

  @JsonProperty("newPx")
  private String amendedPrice;
}
