package org.knowm.xchange.coinjar;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum CoinjarOrderFlags implements IOrderFlags {
  // See
  // https://docs.exchange.coinjar.com/trading-api/#/reference/orders/order-collection/create-an-order for options
  GTC, // Good Till Cancelled  - Default
  IOC, // Immediate Or Cancel
  MOC, // Maker Or Cancel
  AO // Auction Only
}
