package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import org.knowm.xchange.dto.Order.OrderType;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexPublicTrade {

  private long tradeId;
  private long timestamp;
  private BigDecimal amount;
  private BigDecimal price;

  public BitfinexPublicTrade() {}

  public BitfinexPublicTrade(
      long tradeId,
      long timestamp,
      BigDecimal amount,
      BigDecimal price) {

    this.tradeId = tradeId;
    this.timestamp = timestamp;
    this.amount = amount;
    this.price = price;
  }

  public long getTradeId() {

    return tradeId;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public OrderType getType() {

    return getAmount().signum() == -1 ? OrderType.BID : OrderType.ASK;
  }

  public BigDecimal getPrice() {

    return price;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexPublicTrade [tradeId=");
    builder.append(tradeId);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", price=");
    builder.append(price);
    builder.append("]");
    return builder.toString();
  }
}
