package org.knowm.xchange.bitflyer.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitflyerOrderbookEntry {

  private final BigDecimal price;
  private final BigDecimal size;

  public BitflyerOrderbookEntry(@JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size) {

    this.price = price;
    this.size = size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }
}
