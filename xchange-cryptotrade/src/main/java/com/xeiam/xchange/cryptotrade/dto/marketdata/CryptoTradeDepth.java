package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradeDepth extends CryptoTradeBaseResponse {

  private final List<CryptoTradePublicOrder> asks;
  private final List<CryptoTradePublicOrder> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   * @param status
   * @param error
   */
  public CryptoTradeDepth(@JsonProperty("asks") List<CryptoTradePublicOrder> asks, @JsonProperty("bids") List<CryptoTradePublicOrder> bids,
      @JsonProperty("status") String status, @JsonProperty("error") String error) {

    super(status, error);
    this.asks = asks;
    this.bids = bids;
  }

  public List<CryptoTradePublicOrder> getAsks() {

    return asks;
  }

  public List<CryptoTradePublicOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "CryptoTradeDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
