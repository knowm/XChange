package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/** Author: Max Gao (gaamox@tutanota.com) Created: 09-06-2021 */
/** Response DTO received from placing, cancelling, and amending orders * */
@Getter
public class OkexOrderResponse {
  @JsonProperty("ordId")
  private String orderId;

  @JsonProperty("clOrdId")
  private String clientOrderId;

  @JsonProperty("tag")
  private String orderTag;

  @JsonProperty("sCode")
  private String code;

  @JsonProperty("sMsg")
  private String message;
}
