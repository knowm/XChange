package org.knowm.xchange.livecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class LivecoinTrade {

  private final Long time;
  private final Long id;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final String type;

  public LivecoinTrade(@JsonProperty("time") Long time, @JsonProperty("id") Long id, @JsonProperty("price") BigDecimal price,
                       @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("type") String type) {
    super();

    this.time = time;
    this.id = id;
    this.price = price;
    this.quantity = quantity;
    this.type = type;
  }

  public Long getTime() {
    return time;
  }

  public Long getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "LivecoinTrade [time=" + time + ", id=" + id + ", price=" + price + ", quantity=" + quantity + ", type=" + type + "]";
  }
}
