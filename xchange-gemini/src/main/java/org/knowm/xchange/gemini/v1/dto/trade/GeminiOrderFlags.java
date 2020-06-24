package org.knowm.xchange.gemini.v1.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum GeminiOrderFlags implements IOrderFlags {
  /**
   * An immediate-or-cancel order can be partially or completely filled, but any portion of the
   * order that cannot be filled immediately will be cancelled rather than left on the order book.
   */
  IMMEDIATE_OR_CANCEL,

  /**
   * A post-only order will only be placed if no portion of it fills immediately; this guarantees
   * you will never pay the taker fee on any part of the order that fills.
   */
  POST_ONLY,
  /**
   * This order will only remove liquidity from the order book.
   *
   * <p>It will fill the entire order immediately or cancel.
   *
   * <p>If the order doesn't fully fill immediately, the response back from the API will indicate
   * that the order has already been canceled ("is_cancelled": true in JSON).
   */
  FILL_OR_KILL,
  /**
   * This order will be added to the auction-only book for the next auction for this symbol. The
   * order may be cancelled up until the the auction locks, after which cancel requests will be
   * rejected
   */
  AUCTION_ONLY,
  /**
   * An Indication of Interest (IOI) represents a request for liquidity from block trading
   * market-makers for this symbol.
   */
  INDICATION_OF_INTEREST
}
