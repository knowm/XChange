package org.knowm.xchange.bitrue.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class BitrueSymbolPrice {

  public final String symbol;
  public final BigDecimal price;

  public BitrueSymbolPrice(
      @JsonProperty("symbol") String symbol, @JsonProperty("price") BigDecimal price) {
    this.symbol = symbol;
    this.price = price;
  }
}
