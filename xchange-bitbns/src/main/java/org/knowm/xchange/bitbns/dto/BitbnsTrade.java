package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitbnsTrade {

  private BigDecimal price;
  private BigDecimal quantity;
  private String symbol;
  private Long timestamp;
  private boolean maker;

  public BitbnsTrade(
      @JsonProperty("p") BigDecimal price,
      @JsonProperty("q") BigDecimal quantity,
      @JsonProperty("s") String symbol,
      @JsonProperty("T") Long timestamp,
      @JsonProperty("m") boolean maker) {
    this.price = price;
    this.quantity = quantity;
    this.symbol = symbol;
    this.timestamp = timestamp;
    this.maker = maker;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getSymbol() {
    return symbol;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public boolean isMaker() {
    return maker;
  }

  @Override
  public String toString() {
    return "BitbnsTrade [price="
        + price
        + ", quantity="
        + quantity
        + ", symbol="
        + symbol
        + ", timestamp="
        + timestamp
        + ", maker="
        + maker
        + "]";
  }
}
