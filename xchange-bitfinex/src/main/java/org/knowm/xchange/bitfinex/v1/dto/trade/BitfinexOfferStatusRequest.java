package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class BitfinexOfferStatusRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("order_id")
  @JsonRawValue
  private long orderId;

  public BitfinexOfferStatusRequest(String nonce, long orderId) {

    this.request = "/v1/offer/status";
    this.orderId = orderId;
    this.nonce = nonce;
  }

  public String getOrderId() {

    return String.valueOf(orderId);
  }
}
