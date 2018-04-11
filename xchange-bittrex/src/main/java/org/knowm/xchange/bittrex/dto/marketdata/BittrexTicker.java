package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexTicker {

  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal last;

  public BittrexTicker(
      @JsonProperty("Bid") BigDecimal bid,
      @JsonProperty("Ask") BigDecimal ask,
      @JsonProperty("Last") BigDecimal last) {

    this.bid = bid;
    this.ask = ask;
    this.last = last;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getLast() {
    return last;
  }

  @Override
  public String toString() {

    return "BittrexTicker [bid=" + bid + ", ask= " + ask + ", last=" + last + "]";
  }
}
