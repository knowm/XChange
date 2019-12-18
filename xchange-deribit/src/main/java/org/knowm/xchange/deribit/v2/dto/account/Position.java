package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import org.knowm.xchange.deribit.v2.dto.Direction;
import org.knowm.xchange.deribit.v2.dto.Kind;

@Data
public class Position {
  /** Profit or loss from position */
  @JsonProperty("total_profit_loss")
  private BigDecimal totalProfitLoss;
  /** Only for futures, position size in base currency */
  @JsonProperty("size_currency")
  private BigDecimal sizeCurrency;
  /**
   * Position size for futures size in quote currency (e.g. USD), for options size is in base
   * currency (e.g. BTC)
   */
  private BigDecimal size;
  /** Last settlement price for position's instrument 0 if instrument wasn't settled yet */
  @JsonProperty("settlement_price")
  private BigDecimal settlementPrice;
  /** Realized profit or loss */
  @JsonProperty("realized_profit_loss")
  private BigDecimal realizedProfitLoss;
  /** Open orders margin */
  @JsonProperty("open_orders_margin")
  private BigDecimal openOrdersMargin;
  /** Current mark price for position's instrument */
  @JsonProperty("mark_price")
  private BigDecimal markPrice;
  /** Maintenance margin */
  @JsonProperty("maintenance_margin")
  private BigDecimal maintenanceMargin;
  /** Instrument kind, "future" or "option" */
  private Kind kind;
  /** Unique instrument identifier */
  @JsonProperty("instrument_name")
  private String instrumentName;
  /** Initial margin */
  @JsonProperty("initial_margin")
  private BigDecimal initialMargin;
  /** Current index price */
  @JsonProperty("index_price")
  private BigDecimal indexPrice;
  /** Floating profit or loss */
  @JsonProperty("floating_profit_loss")
  private BigDecimal floatingProfitLoss;
  /** Only for futures, estimated liquidation price */
  @JsonProperty("estimated_liquidation_price")
  private BigDecimal estimatedLiquidationPrice;
  /** direction, buy or sell */
  private Direction direction;
  /** Delta parameter */
  private BigDecimal delta;
  /** Average price of trades that built this position */
  @JsonProperty("average_price")
  private BigDecimal averagePrice;
  /** Only for options, average price in USD */
  @JsonProperty("average_price_usd")
  private BigDecimal averagePriceUSD;
  /** Only for options, floating profit or loss in USD */
  @JsonProperty("floating_profit_loss_usd")
  private BigDecimal floatingProfitLossUSD;
}
