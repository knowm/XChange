package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class GateioFuturesAccount {

  @JsonProperty("total")
  private BigDecimal total;

  @JsonProperty("unrealised_pnl")
  private BigDecimal unrealisedPnl;

  @JsonProperty("position_margin")
  private BigDecimal positionMargin;

  @JsonProperty("order_margin")
  private BigDecimal orderMargin;

  @JsonProperty("available")
  private BigDecimal available;

  @JsonProperty("point")
  private BigDecimal point;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("in_dual_mode")
  private Boolean inDualMode;

  @JsonProperty("position_mode")
  private String positionMode;

  @JsonProperty("enable_credit")
  private Boolean enableCredit;

  @JsonProperty("position_initial_margin")
  private BigDecimal positionInitialMargin;

  @JsonProperty("maintenance_margin")
  private BigDecimal maintenanceMargin;

  @JsonProperty("bonus")
  private BigDecimal bonus;

  @JsonProperty("enable_evolved_classic")
  private Boolean enableEvolvedClassic;

  @JsonProperty("cross_order_margin")
  private BigDecimal crossOrderMargin;

  @JsonProperty("cross_initial_margin")
  private BigDecimal crossInitialMargin;

  @JsonProperty("cross_maintenance_margin")
  private BigDecimal crossMaintenanceMargin;

  @JsonProperty("cross_unrealised_pnl")
  private BigDecimal crossUnrealisedPnl;

  @JsonProperty("cross_available")
  private BigDecimal crossAvailable;

  @JsonProperty("cross_margin_balance")
  private BigDecimal crossMarginBalance;

  @JsonProperty("cross_mmr")
  private BigDecimal crossMmr;

  @JsonProperty("cross_imr")
  private BigDecimal crossImr;

  @JsonProperty("isolated_position_margin")
  private BigDecimal isolatedPositionMargin;

  @JsonProperty("enable_new_dual_mode")
  private Boolean enableNewDualMode;

  @JsonProperty("margin_mode")
  private Integer marginMode;

  @JsonProperty("enable_tiered_mm")
  private Boolean enableTieredMm;

}
