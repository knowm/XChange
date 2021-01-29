package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class FtxAccountDto {

  @JsonProperty("backstopProvider")
  private final boolean backstopProvider;

  @JsonProperty("collateral")
  private final BigDecimal collateral;

  @JsonProperty("freeCollateral")
  private final BigDecimal freeCollateral;

  @JsonProperty("initialMarginRequirement")
  private final BigDecimal initialMarginRequirement;

  @JsonProperty("leverage")
  private final BigDecimal leverage;

  @JsonProperty("liquidating")
  private final boolean liquidating;

  @JsonProperty("maintenanceMarginRequirement")
  private final BigDecimal maintenanceMarginRequirement;

  @JsonProperty("makerFee")
  private final BigDecimal makerFee;

  @JsonProperty("marginFraction")
  private final BigDecimal marginFraction;

  @JsonProperty("openMarginFraction")
  private final BigDecimal openMarginFraction;

  @JsonProperty("takerFee")
  private final BigDecimal takerFee;

  @JsonProperty("totalAccountValue")
  private final BigDecimal totalAccountValue;

  @JsonProperty("totalPositionSize")
  private final BigDecimal totalPositionSize;

  @JsonProperty("username")
  private final String username;

  @JsonProperty("positions")
  private final List<FtxPositionDto> positions;

  public FtxAccountDto(
      @JsonProperty("backstopProvider") boolean backstopProvider,
      @JsonProperty("collateral") BigDecimal collateral,
      @JsonProperty("freeCollateral") BigDecimal freeCollateral,
      @JsonProperty("initialMarginRequirement") BigDecimal initialMarginRequirement,
      @JsonProperty("leverage") BigDecimal leverage,
      @JsonProperty("liquidating") boolean liquidating,
      @JsonProperty("maintenanceMarginRequirement") BigDecimal maintenanceMarginRequirement,
      @JsonProperty("makerFee") BigDecimal makerFee,
      @JsonProperty("marginFraction") BigDecimal marginFraction,
      @JsonProperty("openMarginFraction") BigDecimal openMarginFraction,
      @JsonProperty("takerFee") BigDecimal takerFee,
      @JsonProperty("totalAccountValue") BigDecimal totalAccountValue,
      @JsonProperty("totalPositionSize") BigDecimal totalPositionSize,
      @JsonProperty("username") String username,
      @JsonProperty("positions") List<FtxPositionDto> positions) {
    this.backstopProvider = backstopProvider;
    this.collateral = collateral;
    this.freeCollateral = freeCollateral;
    this.initialMarginRequirement = initialMarginRequirement;
    this.leverage = leverage;
    this.liquidating = liquidating;
    this.maintenanceMarginRequirement = maintenanceMarginRequirement;
    this.makerFee = makerFee;
    this.marginFraction = marginFraction;
    this.openMarginFraction = openMarginFraction;
    this.takerFee = takerFee;
    this.totalAccountValue = totalAccountValue;
    this.totalPositionSize = totalPositionSize;
    this.username = username;
    this.positions = positions;
  }

  public boolean getBackstopProvider() {
    return backstopProvider;
  }

  public BigDecimal getCollateral() {
    return collateral;
  }

  public BigDecimal getFreeCollateral() {
    return freeCollateral;
  }

  public BigDecimal getInitialMarginRequirement() {
    return initialMarginRequirement;
  }

  public BigDecimal getLeverage() {
    return leverage;
  }

  public boolean getLiquidating() {
    return liquidating;
  }

  public BigDecimal getMaintenanceMarginRequirement() {
    return maintenanceMarginRequirement;
  }

  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public BigDecimal getMarginFraction() {
    return marginFraction;
  }

  public BigDecimal getOpenMarginFraction() {
    return openMarginFraction;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

  public BigDecimal getTotalAccountValue() {
    return totalAccountValue;
  }

  public BigDecimal getTotalPositionSize() {
    return totalPositionSize;
  }

  public String getUsername() {
    return username;
  }

  public List<FtxPositionDto> getPositions() {
    return positions;
  }

  @Override
  public String toString() {
    return "FtxAccountDto{"
        + "backstopProvider="
        + backstopProvider
        + ", collateral="
        + collateral
        + ", freeCollateral="
        + freeCollateral
        + ", initialMarginRequirement="
        + initialMarginRequirement
        + ", leverage="
        + leverage
        + ", liquidating="
        + liquidating
        + ", maintenanceMarginRequirement="
        + maintenanceMarginRequirement
        + ", makerFee="
        + makerFee
        + ", marginFraction="
        + marginFraction
        + ", openMarginFraction="
        + openMarginFraction
        + ", takerFee="
        + takerFee
        + ", totalAccountValue="
        + totalAccountValue
        + ", totalPositionSize="
        + totalPositionSize
        + ", username='"
        + username
        + '\''
        + ", positions="
        + positions
        + '}';
  }
}
