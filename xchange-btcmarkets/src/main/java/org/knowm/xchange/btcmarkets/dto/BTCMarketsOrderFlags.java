package org.knowm.xchange.btcmarkets.dto;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum BTCMarketsOrderFlags implements IOrderFlags {
  // Time in force, see https://api.btcmarkets.net/doc/v3#tag/Timeinforce
  IOC,
  FOK
}
