package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexCancelOrderMultiRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("order_ids")
  protected long[] orderIds;

  public BitfinexCancelOrderMultiRequest(String nonce, long[] orderIds) {

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

  public long[] getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(long[] orderIds) {
    this.orderIds = orderIds;
  }
}
