package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;

public class CryptoTradePublicTrade {

  private final long id;
  private final long timestamp;
  private final String assetPair;
  private final CryptoTradeOrderType type;
  private final BigDecimal orderAmount;
  private final BigDecimal rate;

  public CryptoTradePublicTrade(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp, @JsonProperty("pair") String assetPair,
      @JsonProperty("type") CryptoTradeOrderType type, @JsonProperty("amount") BigDecimal orderAmount, @JsonProperty("rate") BigDecimal rate) {

    this.id = id;
    this.timestamp = timestamp;
    this.assetPair = assetPair;
    this.type = type;
    this.orderAmount = orderAmount;
    this.rate = rate;
  }

  public long getId() {

    return id;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public String getAssetPair() {

    return assetPair;
  }

  public CryptoTradeOrderType getType() {

    return type;
  }

  public BigDecimal getOrderAmount() {

    return orderAmount;
  }

  public BigDecimal getRate() {

    return rate;
  }

  @Override
  public String toString() {

    return "CryptoTradePublicTrade [id=" + id + ", timestamp=" + timestamp + ", assetPair=" + assetPair + ", type=" + type + ", orderAmount="
        + orderAmount + ", rate=" + rate + "]";
  }
}
