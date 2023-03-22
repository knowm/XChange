package org.knowm.xchange.krakenfutures.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum KrakenFuturesOrderFlags implements IOrderFlags {

  /** These are orders that allow you to be sure to always pay the maker fee. */
  POST_ONLY,
  REDUCE_ONLY
}
