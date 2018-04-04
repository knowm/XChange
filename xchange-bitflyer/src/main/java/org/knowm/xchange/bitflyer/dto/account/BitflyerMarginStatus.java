package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"collateral", "open_position_pnl", "require_collateral", "keep_rate"})
public class BitflyerMarginStatus {
  @JsonProperty("collateral")
  private BigDecimal collateral;

  @JsonProperty("open_position_pnl")
  private BigDecimal openPositionPnl;

  @JsonProperty("require_collateral")
  private BigDecimal requireCollateral;

  @JsonProperty("keep_rate")
  private BigDecimal keepRate;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public BigDecimal getCollateral() {
    return collateral;
  }

  public void setCollateral(BigDecimal collateral) {
    this.collateral = collateral;
  }

  public BigDecimal getOpenPositionPnl() {
    return openPositionPnl;
  }

  public void setOpenPositionPnl(BigDecimal openPositionPnl) {
    this.openPositionPnl = openPositionPnl;
  }

  public BigDecimal getRequireCollateral() {
    return requireCollateral;
  }

  public void setRequireCollateral(BigDecimal requireCollateral) {
    this.requireCollateral = requireCollateral;
  }

  public BigDecimal getKeepRate() {
    return keepRate;
  }

  public void setKeepRate(BigDecimal keepRate) {
    this.keepRate = keepRate;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  @Override
  public String toString() {
    return "BitflyerMarginStatus{"
        + "collateral="
        + collateral
        + ", openPositionPnl="
        + openPositionPnl
        + ", requireCollateral="
        + requireCollateral
        + ", keepRate="
        + keepRate
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
