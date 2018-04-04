package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GeminiTradeResponse {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal timestamp;
  private final String exchange;
  private final String type;
  private final String tradeId;
  private final String orderId;
  private final BigDecimal feeAmount;
  private final String feeCurrency;

  /**
   * Constructor
   *
   * @param price
   * @param amount
   * @param timestamp
   * @param exchange
   * @param type
   * @param tradeId
   * @param orderId
   * @param feeAmount
   * @param feeCurrency
   */
  public GeminiTradeResponse(
      @JsonProperty("price") final BigDecimal price,
      @JsonProperty("amount") final BigDecimal amount,
      @JsonProperty("timestamp") final BigDecimal timestamp,
      @JsonProperty("exchange") final String exchange,
      @JsonProperty("type") final String type,
      @JsonProperty("tid") final String tradeId,
      @JsonProperty("order_id") final String orderId,
      @JsonProperty("fee_amount") final BigDecimal feeAmount,
      @JsonProperty("fee_currency") String feeCurrency) {

    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
    this.exchange = exchange;
    this.type = type;
    this.tradeId = tradeId;
    this.orderId = orderId;
    this.feeAmount = feeAmount;
    this.feeCurrency = feeCurrency;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  public String getType() {

    return type;
  }

  public String getOrderId() {

    return orderId;
  }

  public String getTradeId() {

    return tradeId;
  }

  public BigDecimal getFeeAmount() {

    return feeAmount;
  }

  public String getFeeCurrency() {

    return feeCurrency;
  }

  @Override
  public String toString() {

    final StringBuilder builder = new StringBuilder();
    builder.append("GeminiTradeResponse [price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", exchange=");
    builder.append(exchange);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    builder.append(", tradeId=");
    builder.append(tradeId);
    builder.append("]");
    builder.append(", orderId=");
    builder.append(orderId);
    builder.append(", fee=");
    builder.append(feeAmount);
    builder.append(" ");
    builder.append(feeCurrency);
    builder.append("]");
    return builder.toString();
  }
}
