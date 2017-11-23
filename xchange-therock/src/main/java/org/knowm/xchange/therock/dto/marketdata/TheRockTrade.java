package org.knowm.xchange.therock.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TheRockTrade {

  public enum Side {
    sell, buy, close_long, close_short
  }

  private final BigDecimal amount;
  private final Date date;
  private final BigDecimal price;
  private final long id;
  private final Side side;

  public TheRockTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") Date date, @JsonProperty("price") BigDecimal price,
      @JsonProperty("id") long id, @JsonProperty("side") Side tradeSide) {
    this.amount = amount;
    this.date = date;
    this.price = price;
    this.id = id;
    this.side = tradeSide;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getDate() {
    return date;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public long getId() {
    return id;
  }

  public Side getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "TheRockTrade [amount=" + amount + ", date=" + date + ", price=" + price + ", id=" + id + ", side=" + side + "]";
  }
}
