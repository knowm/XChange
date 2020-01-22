package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CryptowatchSummary {

  private final CryptowatchSummaryPrice price;
  private final BigDecimal volume;
  private final BigDecimal volumeQuote;

  public CryptowatchSummary(
      @JsonProperty("price") CryptowatchSummaryPrice price,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volumeQuote") BigDecimal volumeQuote) {
    this.price = price;
    this.volume = volume;
    this.volumeQuote = volumeQuote;
  }

  public CryptowatchSummaryPrice getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeQuote() {
    return volumeQuote;
  }

  @Override
  public String toString() {
    return "CryptowatchSummary{"
        + "price="
        + price
        + ", volume="
        + volume
        + ", volumeQuote="
        + volumeQuote
        + '}';
  }
}
