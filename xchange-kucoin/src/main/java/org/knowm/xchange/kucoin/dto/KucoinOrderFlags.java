package org.knowm.xchange.kucoin.dto;

import org.knowm.xchange.dto.Order.IOrderFlags;

/** https://docs.kucoin.com/#place-a-new-order */
public enum KucoinOrderFlags implements IOrderFlags {

  /* LimitOrder flags */

  /** Post only flag, invalid when timeInForce is IOC or FOK */
  POST_ONLY
  /** Orders not displayed in order book */
  ,
  HIDDEN
  /** Only visible portion of the order is displayed in the order book */
  ,
  ICEBERG;
}
