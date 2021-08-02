package org.knowm.xchange.deribit.v2.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum OrderFlags implements IOrderFlags {
  /**
   * If true, the order is considered post-only. If the new price would cause the order to be filled
   * immediately (as taker), the price will be changed to be just below the spread.
   *
   * <p>Only valid in combination with time_in_force="good_til_cancelled"
   */
  POST_ONLY,
  /**
   * If an order is considered post-only and this field is set to true then the order is put to
   * order book unmodified or request is rejected and order is canceled.
   *
   * <p>Only valid in combination with "post_only" set to true
   */
  REJECT_POST_ONLY,
  /**
   * If true, the order is considered reduce-only which is intended to only reduce a current
   * position
   */
  REDUCE_ONLY,
  /** Order MMP flag, only for order_type 'limit' */
  MMP
}
