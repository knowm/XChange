package org.knowm.xchange.gdax.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum GDAXOrderFlags implements IOrderFlags {
  /**
   * A fill-or-kill order will either fill in its entirety or be completely aborted.
   */
  FILL_OR_KILL,

  /**
   * An immediate-or-cancel order can be partially or completely filled, but any portion of the order that cannot be filled immediately will be
   * cancelled rather than left on the order book.
   */
  IMMEDIATE_OR_CANCEL,

  /**
   * A post-only order will only be placed if no portion of it fills immediately; this guarantees you will never pay the taker fee on any part of the
   * order that fills.
   */
  POST_ONLY
}
