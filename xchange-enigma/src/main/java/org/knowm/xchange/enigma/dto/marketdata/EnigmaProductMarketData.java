package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnigmaProductMarketData {
  private String productName;
  private double bid;
  private double ask;

  public EnigmaProductMarketData(
      @JsonProperty("product_name") String productName,
      @JsonProperty double bid,
      @JsonProperty double ask) {
    this.productName = productName;
    this.ask = ask;
    this.bid = bid;
  }

  public String getProductName() {
    return this.productName;
  }

  public double getBid() {
    return this.bid;
  }

  public double getAsk() {
    return this.ask;
  }
}
