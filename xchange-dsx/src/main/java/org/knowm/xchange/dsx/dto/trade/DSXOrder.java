package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @author Mikhail Wall
 */

public class DSXOrder {

  private final String pair;
  private final Type type;
  private final BigDecimal volume;
  private final BigDecimal remainingVolume;
  private final BigDecimal rate;
  private final int status;
  private final OrderType orderType;
  private final String timestampCreated;

  public DSXOrder(@JsonProperty("pair") String pair, @JsonProperty("type") Type type, @JsonProperty("volume") BigDecimal volume, @JsonProperty("remainingVolume") BigDecimal remainingVolume,
                  @JsonProperty("rate") BigDecimal rate, @JsonProperty("status") int status,
                  @JsonProperty("orderType") OrderType orderType, @JsonProperty("timestampCreated") String timestampCreated) {

    this.pair = pair;
    this.type = type;
    this.volume = volume;
    this.remainingVolume = remainingVolume;
    this.rate = rate;
    this.status = status;
    this.orderType = orderType;
    this.timestampCreated = timestampCreated;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getRemainingVolume() {
    return remainingVolume;
  }

  public String getTimestampCreated() {
    return timestampCreated;
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

  @Override
  public String toString() {
    return "DSXOrder{" +
            "pair='" + pair + '\'' +
            ", type=" + type +
            ", volume=" + volume +
            ", remainingVolume=" + remainingVolume +
            ", rate=" + rate +
            ", status=" + status +
            ", orderType=" + orderType +
            ", timestampCreated='" + timestampCreated + '\'' +
            '}';
  }

  public enum Type {
    buy, sell
  }

  public enum OrderType {
    limit, market
  }
}
