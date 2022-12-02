package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CoinmateSellFixRateResponseData {
  private final String rateId;

  private final BigDecimal totalReceived;

  private final BigDecimal amount;

  private final BigDecimal rate;

  private final String currencyPair;

  private final Long expiresAt;

  public CoinmateSellFixRateResponseData(
      @JsonProperty("rateId") String rateId,
      @JsonProperty("totalReceived") BigDecimal totalReceived,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("expiresAt") Long expiresAt,
      @JsonProperty("amount") BigDecimal amount
  ) {
    this.rateId = rateId;
    this.totalReceived = totalReceived;
    this.amount = amount;
    this.rate = rate;
    this.currencyPair = currencyPair;
    this.expiresAt = expiresAt;
  }

  public String getRateId() {
    return rateId;
  }

  public BigDecimal getTotalReceived() {
    return totalReceived;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public Long getExpiresAt() {
    return expiresAt;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
