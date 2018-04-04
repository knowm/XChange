package org.knowm.xchange.bitfinex.v1.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum BitfinexOrderFlags implements IOrderFlags {

  /**
   * This type of order is a limit order that must be filled in its entirety or cancelled (killed).
   */
  FILL_OR_KILL,

  /**
   * This is an order which does not appear in the orderbook, and thus doesn't influence other
   * market participants. the taker fee will apply to any trades.
   */
  HIDDEN,

  /** These are orders that allow you to be sure to always pay the maker fee. */
  POST_ONLY,

  /**
   * For order amends indicates that the new order should use the remaining amount of the original
   * order.
   */
  USE_REMAINING,

  /**
   * This type of order a margin order that is leveraged in line with bitfinex current leverage
   * rates.
   */
  MARGIN,

  /** Trailing stop order */
  TRAILING_STOP,

  /** Stop order */
  STOP
}
