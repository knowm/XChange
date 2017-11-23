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
  private final BigDecimal volume;
  private final BigDecimal rate;
  private final int status;
  private final OrderType orderType;

  public DSXOrder(@JsonProperty("pair") String pair, @JsonProperty("type") Type type, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("rate") BigDecimal rate, @JsonProperty("status") int status,
      @JsonProperty("orderType") OrderType orderType) {

    this.pair = pair;
    this.type = type;
    this.volume = volume;
    this.rate = rate;
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
    return volume;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public int getStatus() {
    return status;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public String toString() {

    return MessageFormat.format("DSXOrder[pair=''{0}'', type={1}, volume={2}, rate={3}, status={4}, orderType={5}",
        pair, type, volume, rate, status, orderType);
  }

  public enum Type {
    buy, sell
  }

  public enum OrderType {
    limit, market
  }
}
