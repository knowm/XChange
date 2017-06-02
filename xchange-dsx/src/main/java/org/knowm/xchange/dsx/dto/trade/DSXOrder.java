package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXOrder {

  private final String pair;
  private final Type type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final Long timestampCreated;
  private final int status;
  private final OrderType orderType;

  public DSXOrder(@JsonProperty("pair") String pair, @JsonProperty("type") Type type, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("rate") BigDecimal rate, @JsonProperty("timestamp_created") Long timestampCreated, @JsonProperty("status") int status,
      @JsonProperty("order_type") OrderType orderType) {

    this.pair = pair;
    this.type = type;
    this.amount = amount;
    this.rate = rate;
    this.timestampCreated = timestampCreated;
    this.status = status;
    this.orderType = orderType;
  }

  public String getPair() {
    return pair;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public Long getTimestampCreated() {
    return timestampCreated;
  }

  public int getStatus() {
    return status;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public String toString() {

    return MessageFormat.format("DSXOrder[pair=''{0}'', type={1}, amount={2}, rate={3}, timestamp_created={4}, status={5}, order_type={6}",
        pair, type, amount, rate, timestampCreated, status, orderType);
  }

  public enum Type {
    buy, sell
  }

  public enum OrderType {
    limit, market
  }
}
