package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GateioPositionLeverageUpdate {
  @JsonProperty("leverage")
  private final BigDecimal leverage;

  @JsonProperty("cross_leverage_limit")
  private final BigDecimal crossLeverageLimit;

  public GateioPositionLeverageUpdate(
      @JsonProperty("leverage") BigDecimal leverage,
      @JsonProperty("cross_leverage_limit") BigDecimal crossLeverageLimit) {
    this.leverage = leverage;
    this.crossLeverageLimit = crossLeverageLimit;
  }
}
