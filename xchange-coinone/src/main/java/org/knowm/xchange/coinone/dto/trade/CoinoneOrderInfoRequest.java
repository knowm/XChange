package org.knowm.xchange.coinone.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneOrderInfoRequest {

  @JsonProperty("access_token")
  protected String accessTocken;

  @JsonProperty("nonce")
  protected Long nonce;

  @JsonProperty("order_id")
  protected String orderId;

  @JsonProperty("currency")
  protected String currency;

  /**
   * Constructor
   *
   * @param nonce
   */
  public CoinoneOrderInfoRequest(String accessTocken, Long nonce, String orderId, String currency) {

    this.accessTocken = accessTocken;
    this.nonce = nonce;
    this.orderId = orderId;
    this.currency = currency;
  }

  public String getAccessTocken() {
    return accessTocken;
  }

  public void setAccessTocken(String accessTocken) {
    this.accessTocken = accessTocken;
  }

  public Long getNonce() {
    return nonce;
  }

  public void setNonce(Long nonce) {
    this.nonce = nonce;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
