package com.xeiam.xchange.lakebtc.dto.marketdata;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class LakeBTCOrderBook {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public LakeBTCOrderBook(@JsonProperty("asks") BigDecimal[][] asks, @JsonProperty("bids") BigDecimal[][] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BigDecimal[][] getAsks() {

    return asks;
  }

  public BigDecimal[][] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    StringBuilder asksBuilder = new StringBuilder();
    StringBuilder bidsBuilder = new StringBuilder();

    for (BigDecimal[] ask : getAsks()) {
      asksBuilder.append(Arrays.toString(ask) + ";");
    }

    for (BigDecimal[] bid : getBids()) {
      bidsBuilder.append(Arrays.toString(bid) + ";");
    }

    return "LakeBTCOrderBook{" + "asks=" + asksBuilder + ", bids=" + bidsBuilder + '}';
  }
}