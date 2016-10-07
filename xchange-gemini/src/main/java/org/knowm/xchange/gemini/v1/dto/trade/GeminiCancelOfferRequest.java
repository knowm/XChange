package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class GeminiCancelOfferRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("offer_id")
  @JsonRawValue
  private int offerId;

  public GeminiCancelOfferRequest(String nonce, int offerId) {

    this.request = "/v1/offer/cancel";
    this.nonce = nonce;
    this.offerId = offerId;
  }

  public String getOrderId() {

    return String.valueOf(offerId);
  }
}
