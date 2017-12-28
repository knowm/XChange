package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BinanceSymbolPrice {

  private final String symbol;
  private final BigDecimal price;

  public BinanceSymbolPrice(@JsonProperty("symbol") String symbol, @JsonProperty("price") BigDecimal price) {
    this.symbol = symbol;
    this.price = price;
  }

  public CurrencyPair getCurrencyPair() {
    return CurrencyPairDeserializer.getCurrencyPairFromString(symbol);
  }
  
  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
