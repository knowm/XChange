package info.bitrich.xchangestream.poloniex2.dto;

import java.math.BigDecimal;

/** Created by Lukas Zaoralek on 11.11.17. */
public class OrderbookModifiedEvent {
  private String type;
  private BigDecimal price;
  private BigDecimal volume;

  public OrderbookModifiedEvent(String type, BigDecimal price, BigDecimal volume) {
    this.type = type;
    this.price = price;
    this.volume = volume;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }
}
