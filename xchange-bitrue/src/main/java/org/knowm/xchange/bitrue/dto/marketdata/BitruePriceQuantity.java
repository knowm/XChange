package org.knowm.xchange.bitrue.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class BitruePriceQuantity {

  public final String symbol;
  public final BigDecimal bidPrice;
  public final BigDecimal bidQty;
  public final BigDecimal askPrice;
  public final BigDecimal askQty;

  public BitruePriceQuantity(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("bidPrice") BigDecimal bidPrice,
      @JsonProperty("bidQty") BigDecimal bidQty,
      @JsonProperty("askPrice") BigDecimal askPrice,
      @JsonProperty("askQty") BigDecimal askQty) {
    this.symbol = symbol;
    this.bidPrice = bidPrice;
    this.bidQty = bidQty;
    this.askPrice = askPrice;
    this.askQty = askQty;
  }
}
