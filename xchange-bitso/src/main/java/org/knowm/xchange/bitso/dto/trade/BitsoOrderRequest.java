package org.knowm.xchange.bitso.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Request DTO for placing orders in Bitso API v3
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/place-an-order">Place an Order</a>
 */
@Value
@Builder
@Jacksonized
public class BitsoOrderRequest {

  /** The book to use (e.g., "btc_mxn") */
  private final String book;

  /** The order's side. Valid values: buy and sell */
  private final BitsoOrderSide side;

  /** The order's type. Valid values: market and limit */
  private final BitsoOrderType type;

  /**
   * The amount in the major currency for the order Required for limit orders, optional for market
   * orders
   */
  private final BigDecimal major;

  /**
   * The amount in the minor currency for the order Only for market orders (not supported for limit
   * orders)
   */
  private final BigDecimal minor;

  /** The price per unit of major. Use only with limit orders */
  private final BigDecimal price;

  /**
   * The order's client-supplied, unique ID (optional) Valid character set: a-z (lower and
   * uppercase), 0-9, underscore, dash Maximum length = 40 characters
   */
  private final String originId;

  /** The price per unit of major at which to stop and place the order (stop orders) */
  private final BigDecimal stop;

  /**
   * The period a limit order remains active before it is executed or expires Valid values:
   * goodtillcancelled, fillorkill, immediateorcancel, postonly
   */
  private final BitsoTimeInForce timeInForce;
}
