package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkePrices {

  @JsonProperty("Volume")
  private final double volume;

  @JsonProperty("Price")
  private final double price;

  public LykkePrices(@JsonProperty("Volume") double volume, @JsonProperty("Price") double price) {
    this.volume = volume;
    this.price = price;
  }

  public double getVolume() {
    return volume;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Prices{" + "volume=" + volume + ", price=" + price + '}';
  }
}
