package org.knowm.xchange.mexbt.dto.streaming;

import java.math.BigDecimal;
import java.util.Date;

public class MeXBTStreamingTradeOrOrder {

  private final long id;
  private final Date timestamp;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final int action;
  private final int side;

  public MeXBTStreamingTradeOrOrder(long id, Date timestamp, BigDecimal price, BigDecimal quantity, int action, int side) {
    super();
    this.id = id;
    this.timestamp = timestamp;
    this.price = price;
    this.quantity = quantity;
    this.action = action;
    this.side = side;
  }

  public long getId() {
    return id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public int getAction() {
    return action;
  }

  public int getSide() {
    return side;
  }

}
