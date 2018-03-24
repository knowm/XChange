package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class BitfinexOrdersHistoryRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("limit")
  @JsonRawValue
  private long limit;

  /**
   * Constructor
   *
   * @param nonce
   * @param limit
   */
  public BitfinexOrdersHistoryRequest(String nonce, long limit) {

    this.request = "/v1/orders/hist";
    this.limit = limit;
    this.nonce = nonce;
  }

}
