package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.ftx.dto.trade.FtxOrderSide;

public class FtxPositionDto {

  @JsonProperty("cost")
  private final BigDecimal cost;

  @JsonProperty("entryPrice")
  private final BigDecimal entryPrice;

  @JsonProperty("estimatedLiquidationPrice")
  private final BigDecimal estimatedLiquidationPrice;

  @JsonProperty("future")
  private final String future;

  @JsonProperty("initialMarginRequirement")
  private final BigDecimal initialMarginRequirement;

  @JsonProperty("longOrderSize")
  private final BigDecimal longOrderSize;

  @JsonProperty("maintenanceMarginRequirement")
  private final BigDecimal maintenanceMarginRequirement;

  @JsonProperty("netSize")
  private final BigDecimal netSize;

  @JsonProperty("openSize")
  private final BigDecimal openSize;

  @JsonProperty("realizedPnl")
  private final BigDecimal realizedPnl;

  @JsonProperty("shortOrderSize")
  private final BigDecimal shortOrderSize;

  @JsonProperty("side")
  private final FtxOrderSide side;

  @JsonProperty("size")
  private final BigDecimal size;

  @JsonProperty("unrealizedPnl")
  private final BigDecimal unrealizedPnl;

  @JsonProperty("collateralUsed")
  private final BigDecimal collateralUsed;

  public FtxPositionDto(
      @JsonProperty("cost") BigDecimal cost,
      @JsonProperty("entryPrice") BigDecimal entryPrice,
      @JsonProperty("estimatedLiquidationPrice") BigDecimal estimatedLiquidationPrice,
      @JsonProperty("future") String future,
      @JsonProperty("initialMarginRequirement") BigDecimal initialMarginRequirement,
      @JsonProperty("longOrderSize") BigDecimal longOrderSize,
      @JsonProperty("maintenanceMarginRequirement") BigDecimal maintenanceMarginRequirement,
      @JsonProperty("netSize") BigDecimal netSize,
      @JsonProperty("openSize") BigDecimal openSize,
      @JsonProperty("realizedPnl") BigDecimal realizedPnl,
      @JsonProperty("shortOrderSize") BigDecimal shortOrderSize,
      @JsonProperty("side") FtxOrderSide side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("unrealizedPnl") BigDecimal unrealizedPnl,
      @JsonProperty("collateralUsed") BigDecimal collateralUsed) {
    this.cost = cost;
    this.entryPrice = entryPrice;
    this.estimatedLiquidationPrice = estimatedLiquidationPrice;
    this.future = future;
    this.initialMarginRequirement = initialMarginRequirement;
    this.longOrderSize = longOrderSize;
    this.maintenanceMarginRequirement = maintenanceMarginRequirement;
    this.netSize = netSize;
    this.openSize = openSize;
    this.realizedPnl = realizedPnl;
    this.shortOrderSize = shortOrderSize;
    this.side = side;
    this.size = size;
    this.unrealizedPnl = unrealizedPnl;
    this.collateralUsed = collateralUsed;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public BigDecimal getEntryPrice() {
    return entryPrice;
  }

  public BigDecimal getEstimatedLiquidationPrice() {
    return estimatedLiquidationPrice;
  }

  public String getFuture() {
    return future;
  }

  public BigDecimal getInitialMarginRequirement() {
    return initialMarginRequirement;
  }

  public BigDecimal getLongOrderSize() {
    return longOrderSize;
  }

  public BigDecimal getMaintenanceMarginRequirement() {
    return maintenanceMarginRequirement;
  }

  public BigDecimal getNetSize() {
    return netSize;
  }

  public BigDecimal getOpenSize() {
    return openSize;
  }

  public BigDecimal getRealizedPnl() {
    return realizedPnl;
  }

  public BigDecimal getShortOrderSize() {
    return shortOrderSize;
  }

  public FtxOrderSide getSide() {
    return side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getUnrealizedPnl() {
    return unrealizedPnl;
  }

  public BigDecimal getCollateralUsed() {
    return collateralUsed;
  }

  @Override
  public String toString() {
    return "FtxPositionDto{"
        + "cost="
        + cost
        + ", entryPrice="
        + entryPrice
        + ", estimatedLiquidationPrice="
        + estimatedLiquidationPrice
        + ", future="
        + future
        + ", initialMarginRequirement="
        + initialMarginRequirement
        + ", longOrderSize="
        + longOrderSize
        + ", maintenanceMarginRequirement="
        + maintenanceMarginRequirement
        + ", netSize="
        + netSize
        + ", openSize="
        + openSize
        + ", realizedPnl="
        + realizedPnl
        + ", shortOrderSize="
        + shortOrderSize
        + ", side="
        + side
        + ", size="
        + size
        + ", unrealizedPnl="
        + unrealizedPnl
        + ", collateralUsed="
        + collateralUsed
        + '}';
  }
}
