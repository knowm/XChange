package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class HuobiWithdrawFeeRange {

  private final BigDecimal defaultAmount;
  private final BigDecimal minAmount;
  private final BigDecimal maxAmount;
  private final String dynamicSwitch;

  public HuobiWithdrawFeeRange(
      @JsonProperty("default-amount") BigDecimal defaultAmount,
      @JsonProperty("min-amount") BigDecimal minAmount,
      @JsonProperty("max-amount") BigDecimal maxAmount,
      @JsonProperty("dynamic-switch") String dynamicSwitch) {
    this.defaultAmount = defaultAmount;
    this.minAmount = minAmount;
    this.maxAmount = maxAmount;
    this.dynamicSwitch = dynamicSwitch;
  }

  public BigDecimal getDefaultAmount() {
    return defaultAmount;
  }

  public BigDecimal getMinAmount() {
    return minAmount;
  }

  public BigDecimal getMaxAmount() {
    return maxAmount;
  }

  @Override
  public String toString() {
    return "HuobiWithdrawFeeRange [defaultAmount="
        + getDefaultAmount()
        + ", minAmount="
        + getMinAmount()
        + ", maxAmount="
        + getMaxAmount()
        + "]";
  }
}
