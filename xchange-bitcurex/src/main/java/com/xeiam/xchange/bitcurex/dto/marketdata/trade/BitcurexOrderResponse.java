package com.xeiam.xchange.bitcurex.dto.marketdata.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 08/05/15
 * Time: 16:13
 */
public class BitcurexOrderResponse {

  /*
  {"offer_status": "open", "id": "2014/11/24/238/4771"}
   */

  private String offerStatus;
  private String id;

  public BitcurexOrderResponse(@JsonProperty("offer_status") String offerStatus,
                               @JsonProperty("id") String id) {
    this.offerStatus = offerStatus;
    this.id = id;
  }

  public String getOfferStatus() {
    return offerStatus;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return "BitcurexOrderResponse{" +
        "offerStatus='" + offerStatus + '\'' +
        ", id='" + id + '\'' +
        '}';
  }
}
