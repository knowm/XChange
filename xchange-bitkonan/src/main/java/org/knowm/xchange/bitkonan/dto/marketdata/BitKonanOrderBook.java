package org.knowm.xchange.bitkonan.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanOrderBook {

  private final BitKonanOrderBookElement[] asks;
  private final BitKonanOrderBookElement[] bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public BitKonanOrderBook(@JsonProperty("ask") BitKonanOrderBookElement[] asks, @JsonProperty("bid") BitKonanOrderBookElement[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BitKonanOrderBookElement[] getAsks() {

    return asks;
  }

  public BitKonanOrderBookElement[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    StringBuilder asksBuilder = new StringBuilder();
    StringBuilder bidsBuilder = new StringBuilder();

    for (BitKonanOrderBookElement ask : getAsks()) {
      asksBuilder.append(ask.toString() + ";");
    }

    for (BitKonanOrderBookElement bid : getBids()) {
      bidsBuilder.append(bid.toString() + ";");
    }

    return "BitKonanOrderBook{" + "asks=" + asksBuilder + ", bids=" + bidsBuilder + '}';
  }
}