package org.knowm.xchange.deribit.v2.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

public enum OrderFlags implements IOrderFlags {
  POST_ONLY; // for market maker orders
}
