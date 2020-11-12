package org.knowm.xchange.coindcx.dto;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum CoindcxOrderFlags implements IOrderFlags {
  MARKET_ORDER,
  LIMIT_ORDER;
}
