package org.knowm.xchange.bitbay.dto.trade;

import org.knowm.xchange.bitbay.dto.BitbayBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Z. Dolezal
 */
public class BitbayTradeResponse extends BitbayBaseResponse {
  private final long orderId;

  public BitbayTradeResponse(@JsonProperty("order_id") long orderId, @JsonProperty("success") boolean success, @JsonProperty("code") int code,
      @JsonProperty("message") String errorMsg) {

    super(success, code, errorMsg);

    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }
}
