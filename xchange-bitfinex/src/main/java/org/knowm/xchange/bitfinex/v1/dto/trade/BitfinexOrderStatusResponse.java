package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexOrderStatusResponse {

  private final long id;
  private final String symbol;
  private final BigDecimal price;
  private final BigDecimal avgExecutionPrice;
  private final String side;
  private final String type;
  private final BigDecimal timestamp;
  private final boolean isLive;
  private final boolean isCancelled;
  private final boolean wasForced;
  private final BigDecimal originalAmount;
  private final BigDecimal remainingAmount;
  private final BigDecimal executedAmount;

  /**
   * Constructor
   *
   * @param id
   * @param symbol
   * @param price
   * @param avgExecutionPrice
   * @param side
   * @param type
   * @param timestamp
   * @param isLive
   * @param isCancelled
   * @param wasForced
   * @param originalAmount
   * @param remainingAmount
   * @param executedAmount
   */
  public BitfinexOrderStatusResponse(@JsonProperty("order_id") long id, @JsonProperty("symbol") String symbol,
      @JsonProperty("price") BigDecimal price, @JsonProperty("avg_execution_price") BigDecimal avgExecutionPrice, @JsonProperty("side") String side,
      @JsonProperty("type") String type, @JsonProperty("timestamp") BigDecimal timestamp, @JsonProperty("is_live") boolean isLive,
      @JsonProperty("is_cancelled") boolean isCancelled, @JsonProperty("was_forced") boolean wasForced,
      @JsonProperty("original_amount") BigDecimal originalAmount, @JsonProperty("remaining_amount") BigDecimal remainingAmount,
      @JsonProperty("executed_amount") BigDecimal executedAmount) {

    this.id = id;
    this.symbol = symbol;
    this.price = price;
    this.avgExecutionPrice = avgExecutionPrice;
    this.side = side;
    this.type = type;
    this.timestamp = timestamp;
    this.isLive = isLive;
    this.isCancelled = isCancelled;
    this.wasForced = wasForced;
    this.originalAmount = originalAmount;
    this.remainingAmount = remainingAmount;
    this.executedAmount = executedAmount;
  }

  public BigDecimal getExecutedAmount() {

    return executedAmount;
  }

  public BigDecimal getRemainingAmount() {

    return remainingAmount;
  }

  public BigDecimal getOriginalAmount() {

    return originalAmount;
  }

  public boolean getWasForced() {

    return wasForced;
  }


  public String getType() {

    return type;
  }

  public String getSymbol() {

    return symbol;
  }

  public boolean isCancelled() {

    return isCancelled;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getSide() {

    return side;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  public long getId() {

    return id;
  }

  public boolean isLive() {

    return isLive;
  }

  public BigDecimal getAvgExecutionPrice() {

    return avgExecutionPrice;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexOrderStatusResponse [id=");
    builder.append(id);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append(", price=");
    builder.append(price);
    builder.append(", avgExecutionPrice=");
    builder.append(avgExecutionPrice);
    builder.append(", side=");
    builder.append(side);
    builder.append(", type=");
    builder.append(type);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", isLive=");
    builder.append(isLive);
    builder.append(", isCancelled=");
    builder.append(isCancelled);
    builder.append(", wasForced=");
    builder.append(wasForced);
    builder.append(", originalAmount=");
    builder.append(originalAmount);
    builder.append(", remainingAmount=");
    builder.append(remainingAmount);
    builder.append(", executedAmount=");
    builder.append(executedAmount);
    builder.append("]");
    return builder.toString();
  }
}
