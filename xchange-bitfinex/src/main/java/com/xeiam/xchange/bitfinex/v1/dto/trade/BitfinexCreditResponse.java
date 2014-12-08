package com.xeiam.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexCreditResponse {

  private final int id;
  private final String currency;
  private final String status;
  private final BigDecimal rate;
  private final int period;
  private final BigDecimal amount;
  private final BigDecimal timestamp;

  public BitfinexCreditResponse(@JsonProperty("id") int id, @JsonProperty("currency") String currency, @JsonProperty("status") String status, @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("period") int period, @JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") BigDecimal timestamp) {

    this.id = id;
    this.currency = currency;
    this.status = status;
    this.rate = rate;
    this.period = period;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public int getId() {

    return id;
  }

  public String getCurrency() {

    return currency;
  }

  public String getStatus() {

    return status;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public int getPeriod() {

    return period;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "BitfinexCreditResponse [id=" + id + ", currency=" + currency + ", status=" + status + ", rate=" + rate + ", period=" + period + ", amount=" + amount + ", timestamp=" + timestamp + "]";
  }

}
