package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiCancelOrderMultiRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("order_ids")
  protected int[] orderIds;

  public GeminiCancelOrderMultiRequest(String nonce, int[] orderIds) {

    this.request = "/v1/order/cancel/multi";
    this.nonce = nonce;
    this.orderIds = orderIds;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public int[] getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(int[] orderIds) {
    this.orderIds = orderIds;
  }

}
