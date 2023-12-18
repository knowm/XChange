package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import org.knowm.xchange.okcoin.v3.dto.MarginMode;

@Data
public class FuturesPosition {

  /** Account Type: fixed or crossed */
  @JsonProperty("margin_mode")
  private MarginMode marginMode;

  /** ******************************** common properties ********************************* */
  /** Contract ID, e.g. “BTC-USD-180213” */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** Creation date */
  @JsonProperty("created_at")
  private String createdAt;

  /** Last margin adjusting time */
  @JsonProperty("updated_at")
  private String updatedAt;

  /** Quantity of long positions */
  @JsonProperty("long_qty")
  private BigDecimal longQty;

  /** Available positions to be closed for long positions */
  @JsonProperty("long_avail_qty")
  private BigDecimal longAvailQty;

  /** Margin for long positions */
  @JsonProperty("long_margin")
  private BigDecimal longMargin;

  /** Profit and loss ratio of long positions */
  @JsonProperty("long_pnl_ratio")
  private BigDecimal longPnlRatio;

  /** Average price for opening positions */
  @JsonProperty("long_avg_cost")
  private BigDecimal longAvgCost;

  /** Standard settlement price for long positions */
  @JsonProperty("long_settlement_price")
  private BigDecimal longSettlement_Price;

  /** realised profits */
  @JsonProperty("realised_pnl")
  private BigDecimal realisedPnl;

  /** Quantity of short positions */
  @JsonProperty("short_qty")
  private BigDecimal shortQty;

  /** Available positions to be closed for short positions */
  @JsonProperty("short_avail_qty")
  private BigDecimal shortAvailQty;

  /** Margin for short positions */
  @JsonProperty("short_margin")
  private BigDecimal shortMargin;

  /** Profit and loss ratio of short positions */
  @JsonProperty("short_pnl_ratio")
  private BigDecimal shortPnlRatio;

  /** Average price for opening positions */
  @JsonProperty("short_avg_cost")
  private BigDecimal shortAvgCost;

  /** Standard settlement price for short positions */
  @JsonProperty("short_settlement_price")
  private BigDecimal shortSettlementPrice;

  /** Profit of short positions */
  @JsonProperty("short_pnl")
  private BigDecimal shortPnl;

  /** Unrealized profits and losses of short positions */
  @JsonProperty("short_unrealised_pnl")
  private BigDecimal shortUnrealisedPnl;

  @JsonProperty("short_settled_pnl")
  private BigDecimal shortSettledPnl;

  /** Profit of long positions */
  @JsonProperty("long_pnl")
  private BigDecimal longPnl;

  /** Unrealized profits and losses of long positions */
  @JsonProperty("long_unrealised_pnl")
  private BigDecimal longUnrealisedPnl;

  @JsonProperty("long_settled_pnl")
  private BigDecimal longSettledPnl;

  /** ******************************** crossed margin mode ********************************* */

  /** Estimated liquidation price */
  @JsonProperty("liquidation_price")
  private BigDecimal liquidationPrice;

  /** Leverage */
  private BigDecimal leverage;

  /** ******************************** fixed margin mode ********************************* */

  /** Forced liquidation price for long positions */
  @JsonProperty("long_liqui_price")
  private BigDecimal longLiquiPrice;

  /** Leverage for long positions */
  @JsonProperty("long_leverage")
  private BigDecimal longLeverage;

  /** Forced liquidation price for short positions */
  @JsonProperty("short_liqui_price")
  private BigDecimal shortLiquiPrice;

  /** Leverage for short positions */
  @JsonProperty("short_leverage")
  private BigDecimal shortLeverage;

  /** Margin ratio of short positions */
  @JsonProperty("short_margin_ratio")
  private BigDecimal shortMarginRatio;

  /** Maintenance margin ratio of short positions */
  @JsonProperty("short_maint_margin_ratio")
  private BigDecimal shortMaintMarginRatio;

  /** Margin ratio of long positions */
  @JsonProperty("long_margin_ratio")
  private BigDecimal longMarginRatio;

  /** Maintenance margin ratio of long positions */
  @JsonProperty("long_maint_margin_ratio")
  private BigDecimal longMaintMarginRatio;
}
