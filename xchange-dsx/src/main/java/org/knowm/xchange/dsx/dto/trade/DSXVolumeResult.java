package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXVolumeResult {

  private final BigDecimal tradingVolume;
  private final Long tradesCount;
  private final String currency;

  public DSXVolumeResult(@JsonProperty("tradingVolume") BigDecimal tradingVolume, @JsonProperty("tradesCount") Long tradesCount,
      @JsonProperty("currency") String currency) {

    this.tradingVolume = tradingVolume;
    this.tradesCount = tradesCount;
    this.currency = currency;
  }

  public BigDecimal getTradingVolume() {
    return tradingVolume;
  }

  public Long getTradesCount() {
    return tradesCount;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return "DSXVolumeResult{" +
        "tradingVolume=" + tradingVolume +
        ", tradesCount=" + tradesCount +
        ", currency='" + currency + '\'' +
        '}';
  }
}
