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
public class BatchFuturesOrderResponse {

  /**
   * Indicates the result of the request execution.
   */
  @JsonProperty("succeeded")
  private Boolean succeeded;

  /**
   * Error label, present only when the request fails.
   */
  @JsonProperty("label")
  private String label;

  /**
   * Detailed error information, present only when the request fails and details are provided.
   */
  @JsonProperty("detail")
  private String detail;

  /**
   * The contract order ID.
   */
  @JsonProperty("id")
  private Long id;

  /**
   * User ID associated with the order.
   */
  @JsonProperty("user")
  private Integer user;

  /**
   * Order creation time.
   */
  @JsonProperty("create_time")
  private Double createTime;

  /**
   * Order completion time; not returned for unfinished orders.
   */
  @JsonProperty("finish_time")
  private Double finishTime;

  /**
   * Method by which the order was finished. Possible values: filled, cancelled, liquidated, ioc,
   * auto_deleveraged, reduce_only, position_closed, reduce_out, stp.
   */
  @JsonProperty("finish_as")
  private String finishAs;

  /**
   * Order status; possible values: open, finished.
   */
  @JsonProperty("status")
  private String status;

  /**
   * Contract identifier.
   */
  @JsonProperty("contract")
  private String contract;

  /**
   * Trading quantity. Positive value for buy, negative value for sell. Set to 0 for closing
   * orders.
   */
  @JsonProperty("size")
  private Long size;

  /**
   * Display quantity for iceberg orders; 0 for full visibility.
   */
  @JsonProperty("iceberg")
  private Long iceberg;

  /**
   * Order price. Set to 0 with `ioc` in `tif` for market orders.
   */
  @JsonProperty("price")
  private String price;

  /**
   * Indicates if the order is a closing order. Corresponds to the `close` field in requests.
   */
  @JsonProperty("is_close")
  private Boolean isClose;

  /**
   * Indicates if the order is reduce-only. Corresponds to the `reduce_only` field in requests.
   */
  @JsonProperty("is_reduce_only")
  private Boolean isReduceOnly;

  /**
   * Indicates if the order is a forced liquidation.
   */
  @JsonProperty("is_liq")
  private Boolean isLiq;

  /**
   * Time in Force (TIF) strategy; options: gtc, ioc, poc, fok.
   */
  @JsonProperty("tif")
  private String tif;

  /**
   * Remaining unfilled quantity.
   */
  @JsonProperty("left")
  private Long left;

  /**
   * Filled price of the order.
   */
  @JsonProperty("fill_price")
  private String fillPrice;

  /**
   * Custom order information set by the user.
   */
  @JsonProperty("text")
  private String text;

  /**
   * Taker fee rate.
   */
  @JsonProperty("tkfr")
  private String tkfr;

  /**
   * Maker fee rate.
   */
  @JsonProperty("mkfr")
  private String mkfr;

  /**
   * Referrer user ID.
   */
  @JsonProperty("refu")
  private Integer refu;

  /**
   * Self-Trading Prevention Action strategy.
   */
  @JsonProperty("stp_act")
  private String stpAct;

  /**
   * STP (Self-Trading Prevention) user group ID.
   */
  @JsonProperty("stp_id")
  private Integer stpId;
}
