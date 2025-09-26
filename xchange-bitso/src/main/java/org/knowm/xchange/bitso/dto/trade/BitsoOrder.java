package org.knowm.xchange.bitso.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso Order DTO for API v3
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/list-open-orders">List Open Orders</a>
 * @author Piotr Ładyżyński
 */
@Value
@Builder
@Jacksonized
public class BitsoOrder {

  /** The order book symbol (e.g., "btc_mxn") */
  private final String book;

  /** The date and time when the service executed the trade */
  private final String createdAt;

  /** The order's ID */
  private final String oid;

  /** The order's client-supplied, unique ID (if any) */
  private final String originId;

  /** The order's initial major currency amount */
  private final BigDecimal originalAmount;

  /** The order's initial minor currency amount */
  private final BigDecimal originalValue;

  /** The order's price */
  private final BigDecimal price;

  /** The order's side. Possible values: buy and sell */
  private final BitsoOrderSide side;

  /** The order's status. Possible values: queued, open, partially filled */
  private final BitsoOrderStatus status;

  /** The period a limit order remains active before it is executed or expires */
  private final String timeInForce;

  /** The order's type. Possible values: market, limit */
  private final BitsoOrderType type;

  /** The order's unfilled major currency amount */
  private final BigDecimal unfilledAmount;

  /** The date and time when the service updated the trade */
  private final String updatedAt;

  /** The stop price for Stop Orders (optional) */
  private final BigDecimal stop;

  /** The date and time when the service triggered a Stop Order (optional) */
  private final String triggeredAt;
}
