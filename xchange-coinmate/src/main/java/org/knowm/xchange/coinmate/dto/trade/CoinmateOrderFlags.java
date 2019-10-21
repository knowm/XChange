package org.knowm.xchange.coinmate.dto.trade;

import org.knowm.xchange.dto.Order;

public enum CoinmateOrderFlags implements Order.IOrderFlags {
  /** Stop loss price. */
  STOP,
  /** Flag indicating that order should be created as hidden. */
  HIDDEN,
  /**
   * Flag indicating that order should get maker fee only.
   *
   * */
  POST_ONLY,
  /**
   * In case the flag is set: if limit order is not fully settled immediately the remaining part of
   * the order is cancelled at the end of request.
   */
  IMMEDIATE_OR_CANCEL,
  /**
   * Flag indicating that stop loss order should be created as trailing. Valid flag value is 0 or 1.
   * Default value is 0
   */
  TRAILING,
  /** Id of order used to access order in case of not receiving order id */
  CLIENT_ORDER_ID,
}
