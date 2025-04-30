package org.knowm.xchange.bitstamp.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum BitstampOrderFlags implements IOrderFlags {
  /**
   * This type of market order allows setting the amount in either counter or base currency, (the
   * opposite of the usual market order) For buys, the amount is in the counter currency. For sells,
   * the amount is in the base currency.
   */
  INSTANT_MARKET,

  /** Set amount in counter currency (only supported for sell orders). */
  INSTANT_AMOUNT_IN_COUNTER
}
