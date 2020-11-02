package org.knowm.xchange.deribit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import org.knowm.xchange.deribit.v2.dto.Direction;

@Data
public class Order {
  /** Order time in force: "good_til_cancelled", "fill_or_kill", "immediate_or_cancel" */
  @JsonProperty("time_in_force")
  private TimeInForce time_in_force;
  /** true for reduce-only orders only */
  @JsonProperty("reduce_only")
  private boolean reduceOnly;
  /** Profit and loss in base currency. */
  @JsonProperty("profit_loss")
  private BigDecimal profitLoss;
  /** Price in base currency */
  private BigDecimal price;
  public void setPrice(String priceStr) {
    try {
      this.price = new BigDecimal(priceStr);
    } catch (NumberFormatException e) {
      this.price = null;
    }
  }
  /** true for post-only orders only */
  @JsonProperty("post_only")
  private boolean postOnly;
  /** order type, "limit", "market", "stop_limit", "stop_market" */
  @JsonProperty("order_type")
  private OrderType orderType;
  /** Original order type. Optional field */
  @JsonProperty("original_order_type")
  private OrderType originalOrderType;
  /** order state, "open", "filled", "rejected", "cancelled", "untriggered" */
  @JsonProperty("order_state")
  private OrderState orderState;
  /** Unique order identifier */
  @JsonProperty("order_id")
  private String orderId;
  /** Maximum amount within an order to be shown to other traders, 0 for invisible order. */
  @JsonProperty("max_show")
  private BigDecimal maxShow;
  /** The timestamp (seconds since the Unix epoch, with millisecond precision) */
  @JsonProperty("last_update_timestamp")
  private long lastUpdateTimestamp; //      <- millis
  /** user defined label (up to 32 characters) */
  private String label;
  /** true if order was automatically created during liquidation */
  @JsonProperty("is_liquidation")
  private boolean isLiquidation;
  /** Unique instrument identifier */
  @JsonProperty("instrument_name")
  private String instrumentName;
  /**
   * Filled amount of the order. For perpetual and futures the filled_amount is in USD units, for
   * options - in units or corresponding cryptocurrency contracts, e.g., BTC or ETH.
   */
  @JsonProperty("filled_amount")
  private BigDecimal filledAmount;
  /** direction, buy or sell */
  private Direction direction;
  /** The timestamp (seconds since the Unix epoch, with millisecond precision) */
  @JsonProperty("creation_timestamp")
  private long creationTimestamp; //      <- millis
  /** Commission paid so far (in base currency) */
  private BigDecimal commission;
  /** Average fill price of the order */
  @JsonProperty("average_price")
  private BigDecimal averagePrice;
  /** true if created with API */
  private boolean api;
  /**
   * It represents the requested order size. For perpetual and futures the amount is in USD units,
   * for options it is amount of corresponding cryptocurrency contracts, e.g., BTC or ETH.
   */
  private BigDecimal amount;
  /** Option price in USD (Only if advanced="usd") */
  private BigDecimal usd;
  /** advanced type: "usd" or "implv" (Only for options; field is omitted if not applicable). */
  private String advanced;
  /** Implied volatility in percent. (Only if advanced="implv") */
  private BigDecimal implv;
  /** stop price (Only for future stop orders) */
  @JsonProperty("stop_price")
  private BigDecimal stop_price;
  /**
   * Trigger type (Only for stop orders). Allowed values: "index_price", "mark_price", "last_price".
   */
  private String trigger;
  /** Whether the stop order has been triggered (Only for stop orders) */
  private Boolean triggered;
}
