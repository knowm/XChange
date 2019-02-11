package org.knowm.xchange.cryptofacilities.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum CryptoFacilitiesOrderFlags implements IOrderFlags {

  /** These are orders that allow you to be sure to always pay the maker fee. */
  POST_ONLY
}
