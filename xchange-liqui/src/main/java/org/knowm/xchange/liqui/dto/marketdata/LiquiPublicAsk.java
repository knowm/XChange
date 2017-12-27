package org.knowm.xchange.liqui.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LiquiPublicAsk {

  private final BigDecimal price;
  private final BigDecimal volume;

  @JsonCreator
  public LiquiPublicAsk(final List<String> ask) {
    price = new BigDecimal(ask.get(0));
    volume = new BigDecimal(ask.get(1));
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "LiquiPublicAsk{" +
        "price=" + price +
        ", volume=" + volume +
        '}';
  }
}
