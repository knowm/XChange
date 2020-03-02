package info.bitrich.xchangestream.poloniex2.dto;

import java.math.BigDecimal;
import org.knowm.xchange.dto.Order;

/** Created by Lukas Zaoralek on 11.11.17. */
public class TradeEvent {
  private String tradeId;
  private Order.OrderType type;
  private BigDecimal price;
  private BigDecimal size;
  private int timestampSeconds;

  public TradeEvent(
      String tradeId,
      Order.OrderType type,
      BigDecimal price,
      BigDecimal size,
      int timestampSeconds) {
    this.tradeId = tradeId;
    this.type = type;
    this.price = price;
    this.size = size;
    this.timestampSeconds = timestampSeconds;
  }

  public String getTradeId() {
    return tradeId;
  }

  public Order.OrderType getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public int getTimestampSeconds() {
    return timestampSeconds;
  }
}
