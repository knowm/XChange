package org.knowm.xchange.deribit.v2.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum Trigger implements IOrderFlags {
  index_price,
  mark_price,
  last_price;
}
