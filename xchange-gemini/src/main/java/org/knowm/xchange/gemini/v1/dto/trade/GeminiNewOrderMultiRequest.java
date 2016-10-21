package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiNewOrderMultiRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("orders")
  protected GeminiNewOrder[] orders;

  public GeminiNewOrderMultiRequest(String nonce, GeminiNewOrder[] orders) {

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

  public GeminiNewOrder[] getOrders() {

    return orders;
  }

  public void setOrders(GeminiNewOrder[] orders) {

    this.orders = orders;
  }

}
