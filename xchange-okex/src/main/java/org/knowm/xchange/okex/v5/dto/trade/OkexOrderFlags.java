package org.knowm.xchange.okex.v5.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum OkexOrderFlags implements IOrderFlags {

  /**
   * This type of order can be placed to open or close rather than netting controlled by order
   * placement type
   */
  LONG_SHORT
}
