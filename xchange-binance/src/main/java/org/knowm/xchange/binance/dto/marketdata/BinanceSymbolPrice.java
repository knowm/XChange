package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BinanceSymbolPrice {

  public final String symbol;
  public final BigDecimal price;

  public BinanceSymbolPrice(@JsonProperty("symbol") String symbol
      , @JsonProperty("price") BigDecimal price) {
    this.symbol = symbol;
    this.price = price;
  }
}
