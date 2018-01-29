package org.knowm.xchange.bitmex.dto.marketdata;

import java.math.BigDecimal;

import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitmexPublicOrder {

  private final BigDecimal price;
  private final BigDecimal size;
  private final String symbol;
  private final BigDecimal id;
  private final BitmexSide side;

  public BitmexPublicOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("id") BigDecimal id, @JsonProperty("size") BigDecimal size, @JsonProperty("side") BitmexSide side,
      @JsonProperty("symbol") String symbol) {

    this.symbol = symbol;
    this.id = id;
    this.side = side;
    this.size = size;
    this.price = price;

  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return size;
  }

  public BitmexSide getSide() {

    return side;
  }

  public BigDecimal getId() {

    return id;
  }

  public String getSymbol() {

    return symbol;
  }

  @Override
  public String toString() {

    return "BitmexOrder [price=" + price + ", volume=" + size + ", symbol=" + symbol + ", side=" + side + "]";
  }

}
