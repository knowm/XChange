package com.xeiam.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class BitfinexCancelOfferRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("offer_id")
  @JsonRawValue
  private int offerId;

  public BitfinexCancelOfferRequest(String nonce, int offerId) {

    this.request = "/v1/offer/cancel";
    this.nonce = nonce;
    this.offerId = offerId;
  }

  public String getOrderId() {

    return String.valueOf(offerId);
  }
}
