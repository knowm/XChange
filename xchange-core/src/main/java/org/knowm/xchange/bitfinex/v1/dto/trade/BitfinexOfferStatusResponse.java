package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexOfferStatusResponse {

  private final long id;
  private final String currency;
  private final BigDecimal rate;
  private final int period;
  private final String direction;
  private final String type;
  private final BigDecimal timestamp;
  private final boolean isLive;
  private final boolean isCancelled;
  private final BigDecimal originalAmount;
  private final BigDecimal remainingAmount;
  private final BigDecimal executedAmount;

  public BitfinexOfferStatusResponse(@JsonProperty("id") long id, @JsonProperty("currency") String currency, @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("period") int period, @JsonProperty("direction") String direction, @JsonProperty("type") String type,
      @JsonProperty("timestamp") BigDecimal timestamp, @JsonProperty("is_live") boolean isLive, @JsonProperty("is_cancelled") boolean isCancelled,
      @JsonProperty("original_amount") BigDecimal originalAmount, @JsonProperty("remaining_amount") BigDecimal remainingAmount,
      @JsonProperty("executed_amount") BigDecimal executedAmount) {

    this.id = id;
    this.currency = currency;
    this.rate = rate;
    this.period = period;
    this.direction = direction;
    this.type = type;
    this.timestamp = timestamp;
    this.isLive = isLive;
    this.isCancelled = isCancelled;
    this.originalAmount = originalAmount;
    this.remainingAmount = remainingAmount;
    this.executedAmount = executedAmount;
  }

  public long getId() {

    return id;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public int getPeriod() {

    return period;
  }

  public String getDirection() {

    return direction;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  public boolean isLive() {

    return isLive;
  }

  public boolean isCancelled() {

    return isCancelled;
  }

  public BigDecimal getOriginalAmount() {

    return originalAmount;
  }

  public BigDecimal getRemainingAmount() {

    return remainingAmount;
  }

  public BigDecimal getExecutedAmount() {

    return executedAmount;
  }

  @Override
  public String toString() {

    return "BitfinexOfferStatusResponse [id=" + id + ", currency=" + currency + ", rate=" + rate + ", period=" + period + ", direction=" + direction
        + ", type=" + type + ", timestamp=" + timestamp + ", isLive=" + isLive + ", isCancelled=" + isCancelled + ", originalAmount=" + originalAmount
        + ", remainingAmount=" + remainingAmount + ", executedAmount=" + executedAmount + "]";
  }

}
