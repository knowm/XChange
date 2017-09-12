package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXOrderStatusResult {

  private final String pair;
  private final String type;
  private final BigDecimal remainingVolume;
  private final BigDecimal volume;
  private final BigDecimal rate;
  private final Long timestampCreated;
  private final Integer status;
  private final String orderType;
  private final ClientDeal[] deals;

  public DSXOrderStatusResult(@JsonProperty("pair") String pair, @JsonProperty("type") String type, @JsonProperty("remainingVolume") BigDecimal remainingVolume,
      @JsonProperty("volume") BigDecimal volume, @JsonProperty("rate") BigDecimal rate, @JsonProperty("timestampCreated") Long timestampCreated,
      @JsonProperty("status") Integer status, @JsonProperty("orderType") String orderType, @JsonProperty("clientDeals") ClientDeal[] deals) {
    this.pair = pair;
    this.type = type;
    this.remainingVolume = remainingVolume;
    this.volume = volume;
    this.rate = rate;
    this.timestampCreated = timestampCreated;
    this.status = status;
    this.orderType = orderType;
    this.deals = deals;
  }

  public String getPair() {
    return pair;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getRemainingVolume() {
    return remainingVolume;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public Long getTimestampCreated() {
    return timestampCreated;
  }

  public Integer getStatus() {
    return status;
  }

  public String getOrderType() {
    return orderType;
  }

  public ClientDeal[] getDeals() {
    return deals;
  }

  @Override
  public String toString() {
    return "DSXOrderStatusResult{" +
        "pair='" + pair + '\'' +
        ", type='" + type + '\'' +
        ", remainingVolume=" + remainingVolume +
        ", volume=" + volume +
        ", rate=" + rate +
        ", timestampCreated=" + timestampCreated +
        ", status=" + status +
        ", orderType='" + orderType + '\'' +
        ", deals=" + Arrays.toString(deals) +
        '}';
  }
}
