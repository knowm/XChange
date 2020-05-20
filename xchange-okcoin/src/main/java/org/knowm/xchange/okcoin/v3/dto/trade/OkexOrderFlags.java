package org.knowm.xchange.okcoin.v3.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum OkexOrderFlags implements IOrderFlags {

  /** These are orders that allow you to be sure to always pay the maker fee. */
  POST_ONLY;
}
