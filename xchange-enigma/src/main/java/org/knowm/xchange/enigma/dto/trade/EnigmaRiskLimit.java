package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnigmaRiskLimit {

  private double maxQuantityPerTrade;
  private double maxExposure;

  public EnigmaRiskLimit(
      @JsonProperty("max_qty_per_trade") double maxQuantityPerTrade,
      @JsonProperty("max_exposure") double maxExposure) {
    this.maxQuantityPerTrade = maxQuantityPerTrade;
    this.maxExposure = maxExposure;
  }

  public double getMaxExposure() {
    return maxExposure;
  }

  public double getMaxQuantityPerTrade() {
    return maxQuantityPerTrade;
  }
}
