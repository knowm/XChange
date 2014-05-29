package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyPublicOrderbook {

  private final int marketId;
  private final List<CryptsyPublicOrder> buyOrders;
  private final List<CryptsyPublicOrder> sellOrders;

  /**
   * Constructor
   */
  @JsonCreator
  public CryptsyPublicOrderbook(@JsonProperty("marketid") int marketId, @JsonProperty("buyorders") List<CryptsyPublicOrder> buyOrders, @JsonProperty("sellorders") List<CryptsyPublicOrder> sellOrders) {

    this.marketId = marketId;
    this.buyOrders = buyOrders;
    this.sellOrders = sellOrders;
  }

  public int marketID() {

    return marketId;
  }

  public List<CryptsyPublicOrder> buyOrders() {

    return buyOrders;
  }

  public List<CryptsyPublicOrder> sellOrders() {

    return sellOrders;
  }

  @Override
  public String toString() {

    return "CryptsyOrderBook [Market ID='" + marketId + "',Buy Orders='" + buyOrders + "',Sell Orders=" + sellOrders + "]";
  }

}
