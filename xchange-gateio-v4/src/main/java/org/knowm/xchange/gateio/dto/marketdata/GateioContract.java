package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the contract details, including various trading parameters and configurations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GateioContract {

  /**
   * Contract identifier
   */
  @JsonProperty("name")
  private String name;

  /**
   * Contract type: inverse - inverse contract, direct - direct contract
   */
  @JsonProperty("type")
  private String type;

  /**
   * Multiplier to convert the quoted currency to the settlement currency
   */
  @JsonProperty("quanto_multiplier")
  private String quantoMultiplier;

  /**
   * Minimum leverage
   */
  @JsonProperty("leverage_min")
  private String leverageMin;

  /**
   * Maximum leverage
   */
  @JsonProperty("leverage_max")
  private String leverageMax;

  /**
   * Maintenance margin rate
   */
  @JsonProperty("maintenance_rate")
  private String maintenanceRate;

  /**
   * Price marking method: internal - internal trading price, index - external index price
   */
  @JsonProperty("mark_type")
  private String markType;

  /**
   * Current marked price
   */
  @JsonProperty("mark_price")
  private String markPrice;

  /**
   * Current index price
   */
  @JsonProperty("index_price")
  private String indexPrice;

  /**
   * Last transaction price
   */
  @JsonProperty("last_price")
  private String lastPrice;

  /**
   * Maker fee rate; negative values indicate rebates
   */
  @JsonProperty("maker_fee_rate")
  private String makerFeeRate;

  /**
   * Taker fee rate
   */
  @JsonProperty("taker_fee_rate")
  private String takerFeeRate;

  /**
   * Minimum unit for order price
   */
  @JsonProperty("order_price_round")
  private String orderPriceRound;

  /**
   * Minimum unit for mark and liquidation prices
   */
  @JsonProperty("mark_price_round")
  private String markPriceRound;

  /**
   * Current funding rate
   */
  @JsonProperty("funding_rate")
  private String fundingRate;

  /**
   * Funding rate application interval (in seconds)
   */
  @JsonProperty("funding_interval")
  private Integer fundingInterval;

  /**
   * Next funding rate application time
   */
  @JsonProperty("funding_next_apply")
  private Double fundingNextApply;

  /**
   * Deprecated: Base risk limit
   */
  @JsonProperty("risk_limit_base")
  private String riskLimitBase;

  /**
   * Deprecated: Risk limit adjustment step
   */
  @JsonProperty("risk_limit_step")
  private String riskLimitStep;

  /**
   * Deprecated: Maximum risk limit allowed by the contract
   */
  @JsonProperty("risk_limit_max")
  private String riskLimitMax;

  /**
   * Minimum order size
   */
  @JsonProperty("order_size_min")
  private Long orderSizeMin;

  /**
   * Maximum order size
   */
  @JsonProperty("order_size_max")
  private Long orderSizeMax;

  /**
   * Allowed deviation of order price from the mark price
   */
  @JsonProperty("order_price_deviate")
  private String orderPriceDeviate;

  /**
   * Discount rate for referees
   */
  @JsonProperty("ref_discount_rate")
  private String refDiscountRate;

  /**
   * Rebate rate for referrers
   */
  @JsonProperty("ref_rebate_rate")
  private String refRebateRate;

  /**
   * Orderbook update ID
   */
  @JsonProperty("orderbook_id")
  private Long orderbookId;

  /**
   * Current transaction ID
   */
  @JsonProperty("trade_id")
  private Long tradeId;

  /**
   * Cumulative historical transaction size
   */
  @JsonProperty("trade_size")
  private Long tradeSize;

  /**
   * Total long position held by users
   */
  @JsonProperty("position_size")
  private Long positionSize;

  /**
   * Last configuration change time
   */
  @JsonProperty("config_change_time")
  private Double configChangeTime;

  /**
   * Indicates if the contract is in delisting transition
   */
  @JsonProperty("in_delisting")
  private Boolean inDelisting;

  /**
   * Maximum number of open orders allowed
   */
  @JsonProperty("orders_limit")
  private Integer ordersLimit;

  /**
   * Indicates if bonus funds are supported
   */
  @JsonProperty("enable_bonus")
  private Boolean enableBonus;

  /**
   * Indicates if unified accounts are supported
   */
  @JsonProperty("enable_credit")
  private Boolean enableCredit;

  /**
   * Contract creation time
   */
  @JsonProperty("create_time")
  private Double createTime;

  /**
   * Funding rate cap ratio
   */
  @JsonProperty("funding_cap_ratio")
  private String fundingCapRatio;
}
