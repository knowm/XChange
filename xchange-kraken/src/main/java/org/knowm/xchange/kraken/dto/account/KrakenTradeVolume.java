package org.knowm.xchange.kraken.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenTradeVolume {

  private final String currency;
  private final BigDecimal volume;
  private final Map<String, KrakenVolumeFee> fees;

  /**
   * Constructor
   * 
   * @param currency
   * @param volume
   * @param fees
   */
  public KrakenTradeVolume(@JsonProperty("currency") String currency, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("fees") Map<String, KrakenVolumeFee> fees) {

    this.currency = currency;
    this.volume = volume;
    this.fees = fees;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public Map<String, KrakenVolumeFee> getFees() {

    return fees;
  }

  @Override
  public String toString() {

    return "KrakenTradeVolume [currency=" + currency + ", volume=" + volume + ", fees=" + fees + "]";
  }
}
