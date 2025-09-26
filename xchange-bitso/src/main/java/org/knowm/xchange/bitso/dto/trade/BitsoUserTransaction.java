package org.knowm.xchange.bitso.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso User Transaction/Trade DTO for API v3
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/list-user-trades">List Order Trades</a>
 * @author Piotr Ładyżyński
 */
@Value
@Builder
@Jacksonized
public class BitsoUserTransaction {

  /** The order book symbol (e.g., "btc_mxn") */
  private final String book;

  /** The date and time when the service executed the trade */
  private final String createdAt;

  /** The amount charged as trade fee */
  private final BigDecimal feesAmount;

  /** The currency in which the service charged the trade fee */
  private final String feesCurrency;

  /** The major amount traded */
  private final BigDecimal major;

  /** The ticker of the major currency */
  private final String majorCurrency;

  /** The maker's side for this trade (buy or sell) */
  private final String makerSide;

  /** The minor amount traded */
  private final BigDecimal minor;

  /** The ticker of the minor currency */
  private final String minorCurrency;

  /** The order ID */
  private final String oid;

  /** The order's client-supplied, unique ID (if any) */
  private final String originId;

  /** The price per unit of major */
  private final BigDecimal price;

  /** The user's side for this trade. Possible values: buy and sell */
  private final String side;

  /** The trade's ID */
  private final String tid;
}
