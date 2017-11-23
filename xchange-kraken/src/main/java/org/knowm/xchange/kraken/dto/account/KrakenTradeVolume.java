package org.knowm.xchange.kraken.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenTradeVolume {

  private final String currency;
  private final BigDecimal volume;
  private final Map<String, KrakenVolumeFee> feesTaker;
  private final Map<String, KrakenVolumeFee> feesMaker;

  public KrakenTradeVolume(@JsonProperty("currency") String currency, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("fees") Map<String, KrakenVolumeFee> feesTaker, @JsonProperty("fees_maker") Map<String, KrakenVolumeFee> feesMaker) {

    this.currency = currency;
    this.volume = volume;
    this.feesTaker = feesTaker;
    this.feesMaker = feesMaker;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public Map<String, KrakenVolumeFee> getFees() {

    return feesTaker;
  }

  public Map<String, KrakenVolumeFee> getFeesMaker() {

    return feesMaker;
  }

  @Override
  public String toString() {

    return "KrakenTradeVolume [currency=" + currency + ", volume=" + volume + ", feesTaker=" + feesTaker + ", feesMaker=" + feesMaker + "]";
  }
}
