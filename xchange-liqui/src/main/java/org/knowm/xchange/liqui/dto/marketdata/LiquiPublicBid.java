package org.knowm.xchange.liqui.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.List;

public class LiquiPublicBid {

  private final BigDecimal price;
  private final BigDecimal volume;

  @JsonCreator
  public LiquiPublicBid(final List<String> ask) {
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
    return "LiquiPublicBid{" + "price=" + price + ", volume=" + volume + '}';
  }
}
