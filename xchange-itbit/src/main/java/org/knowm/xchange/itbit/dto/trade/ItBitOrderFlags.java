package org.knowm.xchange.itbit.dto.trade;

import org.knowm.xchange.dto.Order;

public enum ItBitOrderFlags implements Order.IOrderFlags {

  /**
   * A post-only order will only be placed if no portion of it fills immediately; this guarantees
   * you will never pay the taker fee on any part of the order that fills.
   */
  POST_ONLY
}
