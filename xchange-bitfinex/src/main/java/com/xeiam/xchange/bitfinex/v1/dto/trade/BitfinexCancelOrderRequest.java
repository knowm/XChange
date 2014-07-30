package com.xeiam.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class BitfinexCancelOrderRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("order_id")
  @JsonRawValue
  private int orderId;

  /**
   * Constructor
   * 
   * @param nonce
   * @param orderId
   */
  public BitfinexCancelOrderRequest(String nonce, int orderId) {

    this.request = "/v1/order/cancel";
    this.orderId = orderId;
    this.nonce = nonce;
  }

  public String getOrderId() {

    return String.valueOf(orderId);
  }

}
