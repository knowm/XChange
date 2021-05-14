package org.knowm.xchange.bx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxAssetPair {

  private final String pairingId;
  private final String primaryCurrency;
  private final String secondaryCurrency;
  private final BigDecimal primaryMin;
  private final BigDecimal secondaryMin;
  private final boolean active;

  public BxAssetPair(
      @JsonProperty("pairing_id") String pairingId,
      @JsonProperty("primary_currency") String primaryCurrency,
      @JsonProperty("secondary_currency") String secondaryCurrency,
      @JsonProperty("primary_min") BigDecimal primaryMin,
      @JsonProperty("secondary_min") BigDecimal secondaryMin,
      @JsonProperty("active") boolean active) {
    this.pairingId = pairingId;
    this.primaryCurrency = primaryCurrency;
    this.secondaryCurrency = secondaryCurrency;
    this.primaryMin = primaryMin;
    this.secondaryMin = secondaryMin;
    this.active = active;
  }

  public String getPrimaryCurrency() {
    return primaryCurrency;
  }

  public String getSecondaryCurrency() {
    return secondaryCurrency;
  }

  public BigDecimal getPrimaryMin() {
    return primaryMin;
  }

  public boolean isActive() {
    return active;
  }

  @Override
  public String toString() {
    return "BxAssetPair{"
        + "pairingId='"
        + pairingId
        + '\''
        + ", primaryCurrency='"
        + primaryCurrency
        + '\''
        + ", secondaryCurrency='"
        + secondaryCurrency
        + '\''
        + ", primaryMin="
        + primaryMin
        + ", secondaryMin="
        + secondaryMin
        + ", active="
        + active
        + '}';
  }
}
