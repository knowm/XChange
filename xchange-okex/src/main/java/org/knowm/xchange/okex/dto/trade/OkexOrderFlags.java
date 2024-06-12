package org.knowm.xchange.okex.dto.trade;

import org.knowm.xchange.dto.Order;

public enum OkexOrderFlags implements Order.IOrderFlags {
  POST_ONLY,
  REDUCE_ONLY,
  OPTIMAL_LIMIT_IOC
}
