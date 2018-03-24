package org.knowm.xchange.gatecoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sumedha
 */
public class GatecoinDepth {

  private final BigDecimal price;
  private final BigDecimal volume;

  public GatecoinDepth(@JsonProperty("price") BigDecimal price, @JsonProperty("volume") BigDecimal volume) {
    this.price = price;
    this.volume = volume;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "GatecoinDepth[ price=" + price + ",volume =" + volume + "]";
  }

}
