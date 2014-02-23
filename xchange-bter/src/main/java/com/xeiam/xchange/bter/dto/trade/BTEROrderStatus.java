package com.xeiam.xchange.bter.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROrderStatus {

  private final String id;
  private final String status;
  private final String tradePair;
  private final String type;
  private final BigDecimal rate;
  private final BigDecimal amount;
  private final BigDecimal initialRate;
  private final BigDecimal initialAmount;

  public BTEROrderStatus(@JsonProperty("id") String id, @JsonProperty("status") String status, @JsonProperty("pair") String tradePair,
                         @JsonProperty("type") String type, @JsonProperty("rate") BigDecimal rate, @JsonProperty("amount") BigDecimal amount,
                         @JsonProperty("initial_rate") BigDecimal initialRate, @JsonProperty("initial_amount") BigDecimal initialAmount) {
    this.id = id;
    this.status = status;
    this.tradePair = tradePair;
    this.type = type;
    this.rate = rate;
    this.amount = amount;
    this.initialRate = initialRate;
    this.initialAmount = initialAmount;
  }

  public String getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public String getTradePair() {
    return tradePair;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getInitialRate() {
    return initialRate;
  }

  public BigDecimal getInitialAmount() {
    return initialAmount;
  }

  @Override
  public String toString() {
    return "BTEROrderStatus{" +
            "id='" + id + '\'' +
            ", status='" + status + '\'' +
            ", tradePair='" + tradePair + '\'' +
            ", type='" + type + '\'' +
            ", rate=" + rate +
            ", amount=" + amount +
            ", initialRate=" + initialRate +
            ", initialAmount=" + initialAmount +
            '}';
  }
}
