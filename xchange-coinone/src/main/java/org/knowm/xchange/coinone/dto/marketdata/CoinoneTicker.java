package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author interwater */
public class CoinoneTicker {

  private String status;
  private CoinoneTickerData data;
  /**
   * @param status
   * @param data
   */
  public CoinoneTicker(
      @JsonProperty("status") String status, @JsonProperty("data") CoinoneTickerData data) {
    this.status = status;
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public CoinoneTickerData getData() {
    return data;
  }
}
