package com.xeiam.xchange.coinsetter.dto.pricealert.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinsetterRemovePriceAlertResponse {

  private final String status;

  /**
   * @param status Status of the remove request (ex: "success")
   */
  public CoinsetterRemovePriceAlertResponse(@JsonProperty("status") String status) {

    super();
    this.status = status;
  }

  public String getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return "CoinsetterRemovePriceAlertResponse [status=" + status + "]";
  }

}
