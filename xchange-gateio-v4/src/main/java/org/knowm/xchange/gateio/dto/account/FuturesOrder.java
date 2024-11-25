package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuturesOrder {

  /**
   * The contract order ID (read-only).
   */
  @JsonProperty("id")
  private Long id;

  /**
   * The user ID (read-only).
   */
  @JsonProperty("user")
  private Integer user;

  /**
   * The order creation time (read-only).
   */
  @JsonProperty("create_time")
  private Double createTime;

  /**
   * The order completion time (read-only). Absent for unfinished orders.
   */
  @JsonProperty("finish_time")
  private Double finishTime;

  /**
   * The completion method (read-only).
   * <p>Possible values:</p>
   * <ul>
   *     <li>filled: Fully filled</li>
   *     <li>cancelled: Canceled by user</li>
   *     <li>liquidated: Force liquidation cancellation</li>
   *     <li>ioc: Not fully filled immediately due to IOC setting</li>
   *     <li>auto_deleveraged: Auto-deleveraged cancellation</li>
   *     <li>reduce_only: Position increase canceled due to reduce-only or close setting</li>
   *     <li>position_closed: Order canceled because the position was closed</li>
   *     <li>reduce_out: Reduce-only order removed due to low matching probability</li>
   *     <li>stp: Order canceled due to self-trading prevention</li>
   * </ul>
   */
  @JsonProperty("finish_as")
  private String finishAs;

  /**
   * The order status (read-only).
   * <p>Possible values:</p>
   * <ul>
   *     <li>open: Awaiting processing</li>
   *     <li>finished: Completed order</li>
   * </ul>
   */
  @JsonProperty("status")
  private String status;

  /**
   * The contract identifier (required).
   */
  @JsonProperty("contract")
  private String contract;

  /**
   * The trading quantity (required). Positive for buy, negative for sell. Set to 0 for closing
   * orders.
   */
  @JsonProperty("size")
  private Long size;

  /**
   * The iceberg order display quantity. Set to 0 to avoid hiding (read-only).
   */
  @JsonProperty("iceberg")
  private Long iceberg;

  /**
   * The order price. A value of 0 with TIF set to IOC indicates a market order.
   */
  @JsonProperty("price")
  private String price;

  /**
   * When true, executes a close operation. The size should be set to 0 (write-only).
   */
  @JsonProperty("close")
  private Boolean close;

  /**
   * Indicates if this is a close order (read-only).
   */
  @JsonProperty("is_close")
  private Boolean isClose;

  /**
   * When true, marks the order as reduce-only (write-only).
   */
  @JsonProperty("reduce_only")
  private Boolean reduceOnly;

  /**
   * Indicates if this is a reduce-only order (read-only).
   */
  @JsonProperty("is_reduce_only")
  private Boolean isReduceOnly;

  /**
   * Indicates if this is a forced liquidation order (read-only).
   */
  @JsonProperty("is_liq")
  private Boolean isLiq;

  /**
   * The time-in-force strategy.
   * <p>Possible values:</p>
   * <ul>
   *     <li>gtc: Good-Till-Cancelled</li>
   *     <li>ioc: Immediate-Or-Cancel</li>
   *     <li>poc: Pending-Or-Cancel</li>
   *     <li>fok: Fill-Or-Kill</li>
   * </ul>
   */
  @JsonProperty("tif")
  private String tif;

  /**
   * The unfilled quantity (read-only).
   */
  @JsonProperty("left")
  private Long left;

  /**
   * The fill price (read-only).
   */
  @JsonProperty("fill_price")
  private String fillPrice;

  /**
   * Custom order information set by the user.
   */
  @JsonProperty("text")
  private String text;

  /**
   * The taker fee rate (read-only).
   */
  @JsonProperty("tkfr")
  private String takerFeeRate;

  /**
   * The maker fee rate (read-only).
   */
  @JsonProperty("mkfr")
  private String makerFeeRate;

  /**
   * The referrer user ID (read-only).
   */
  @JsonProperty("refu")
  private Integer referrerId;

  /**
   * In dual-position mode, sets the direction for closing positions (write-only).
   */
  @JsonProperty("auto_size")
  private String autoSize;

  /**
   * The STP user group ID. Orders within the same STP group cannot self-trade (read-only).
   */
  @JsonProperty("stp_id")
  private Integer stpId;

  /**
   * Self-Trading Prevention (STP) action. Users can set custom self-trading prevention strategies.
   * <p>Possible values:</p>
   * <ul>
   *     <li>cn: Cancel newest</li>
   *     <li>co: Cancel oldest</li>
   *     <li>cb: Cancel both</li>
   *     <li>-: No STP applied</li>
   * </ul>
   */
  @JsonProperty("stp_act")
  private String stpAct;

  /**
   * Remarks when the user modifies the order (read-only).
   */
  @JsonProperty("amend_text")
  private String amendText;

  /**
   * Additional business information (read-only).
   */
  @JsonProperty("biz_info")
  private String bizInfo;
}
