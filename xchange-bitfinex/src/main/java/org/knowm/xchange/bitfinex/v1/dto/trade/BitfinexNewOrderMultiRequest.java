package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexNewOrderMultiRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("orders")
  protected BitfinexNewOrder[] orders;

  public BitfinexNewOrderMultiRequest(String nonce, BitfinexNewOrder[] orders) {

    this.request = "/v1/order/new/multi";
    this.nonce = nonce;
    this.orders = orders;
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

  public BitfinexNewOrder[] getOrders() {

    return orders;
  }

  public void setOrders(BitfinexNewOrder[] orders) {

    this.orders = orders;
  }
}
