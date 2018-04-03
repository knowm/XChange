package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.dto.Order.OrderType;

public class BitfinexActivePositionsResponse {

  private final long id;
  private final String symbol;
  private final String status;
  private final BigDecimal base;
  private final BigDecimal amount;
  private final BigDecimal timestamp;
  private final BigDecimal swap;
  private final BigDecimal pnl;
  private final OrderType orderType;

  public BitfinexActivePositionsResponse(
      @JsonProperty("id") long id,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("status") String status,
      @JsonProperty("base") BigDecimal base,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("timestamp") BigDecimal timestamp,
      @JsonProperty("swap") BigDecimal swap,
      @JsonProperty("pl") BigDecimal pnl) {

    this.id = id;
    this.symbol = symbol;
    this.status = status;
    this.base = base;
    this.amount = amount;
    this.timestamp = timestamp;
    this.swap = swap;
    this.pnl = pnl;

    this.orderType = amount.signum() < 0 ? OrderType.ASK : OrderType.BID;
  }

  public long getId() {

    return id;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getStatus() {

    return status;
  }

  public BigDecimal getBase() {

    return base;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  public BigDecimal getSwap() {

    return swap;
  }

  public BigDecimal getPnl() {

    return pnl;
  }

  public OrderType getOrderType() {

    return orderType;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexActivePositionsResponse [id=");
    builder.append(id);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append(", status=");
    builder.append(status);
    builder.append(", base=");
    builder.append(base);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", swap=");
    builder.append(swap);
    builder.append(", pnl=");
    builder.append(pnl);
    builder.append(", orderType=");
    builder.append(orderType);
    builder.append("]");
    return builder.toString();
  }
}
