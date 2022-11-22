package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CoinmateFixRateResponseData {
  private final String rateId;

  private final BigDecimal total;

  private final BigDecimal rate;

  private final String currencyPair;

  private final Long expiresAt;

  public CoinmateFixRateResponseData(
      @JsonProperty("rateId") String rateId,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("expiresAt") Long expiresAt) {
    this.rateId = rateId;
    this.total = total;
    this.rate = rate;
    this.currencyPair = currencyPair;
    this.expiresAt = expiresAt;
  }

  public String getRateId() {
    return rateId;
  }

  public BigDecimal getTotal() {
    return total;
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
}
