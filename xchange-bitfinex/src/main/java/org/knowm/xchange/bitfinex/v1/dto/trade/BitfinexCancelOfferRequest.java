package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class BitfinexCancelOfferRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("offer_id")
  @JsonRawValue
  private long offerId;

  public BitfinexCancelOfferRequest(String nonce, long offerId) {

    this.request = "/v1/offer/cancel";
    this.nonce = nonce;
    this.offerId = offerId;
  }

  public String getOrderId() {

    return String.valueOf(offerId);
  }
}
