package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Mikhail Wall */
public class DSXCommission {

  private BigDecimal tradingVolume;
  private BigDecimal takerCommission;
  private BigDecimal makerCommission;

  public DSXCommission(
      @JsonProperty("tradingVolume") BigDecimal tradingVolume,
      @JsonProperty("takerCommission") BigDecimal takerCommission,
      @JsonProperty("makerCommission") BigDecimal makerCommission) {
    this.tradingVolume = tradingVolume;
    this.takerCommission = takerCommission;
    this.makerCommission = makerCommission;
  }

  public BigDecimal getTradingVolume() {
    return tradingVolume;
  }

  public BigDecimal getTakerCommission() {
    return takerCommission;
  }

  public BigDecimal getMakerCommission() {
    return makerCommission;
  }
}
