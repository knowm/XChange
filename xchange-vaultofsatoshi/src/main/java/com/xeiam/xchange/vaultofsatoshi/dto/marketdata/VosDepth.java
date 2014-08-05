package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from VaultOfSatoshi
 */

public final class VosDepth {

  private final List<VosOrder> asks;
  private final List<VosOrder> bids;
  private final long timestamp;
  private final String order_currency;
  private final String payment_currency;

  @JsonCreator
  public VosDepth(@JsonProperty("asks") List<VosOrder> asks, @JsonProperty("bids") List<VosOrder> bids, @JsonProperty("timestamp") long timestamp,
      @JsonProperty("order_currency") String order_currency, @JsonProperty("payment_currency") String payment_currency) {

    this.asks = asks;
    this.bids = bids;
    this.timestamp = timestamp;
    this.order_currency = order_currency;
    this.payment_currency = payment_currency;
  }

  public List<VosOrder> getAsks() {

    return asks;
  }

  public List<VosOrder> getBids() {

    return bids;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public String getPaymentCurrency() {

    return payment_currency;
  }

  public String getOrderCurrency() {

    return order_currency;
  }

  @Override
  public String toString() {

    return "VaultOfSatoshiDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
