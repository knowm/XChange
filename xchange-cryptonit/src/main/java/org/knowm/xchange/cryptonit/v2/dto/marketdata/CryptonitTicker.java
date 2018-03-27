package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Data object representing Ticker from Cryptonit */
public final class CryptonitTicker {

  private final CryptonitRate rate;
  private final CryptonitVolume volume;

  /**
   * @param rate
   * @param volume
   */
  public CryptonitTicker(
      @JsonProperty("rate") CryptonitRate rate, @JsonProperty("volume") CryptonitVolume volume) {

    this.rate = rate;
    this.volume = volume;
  }

  public CryptonitRate getRate() {

    return rate;
  }

  public CryptonitVolume getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return "CryptonitTicker [rate=" + rate + ", volume=" + volume + "]";
  }
}
