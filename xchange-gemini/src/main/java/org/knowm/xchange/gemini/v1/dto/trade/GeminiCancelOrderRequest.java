package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class GeminiCancelOrderRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("order_id")
  @JsonRawValue
  private long orderId;

  /**
   * Constructor
   *
   * @param nonce
   * @param orderId
   */
  public GeminiCancelOrderRequest(String nonce, long orderId) {
    this.request = "/v1/order/cancel";
    this.orderId = orderId;
    this.nonce = nonce;
  }

  public String getOrderId() {
    return String.valueOf(orderId);
  }
}
