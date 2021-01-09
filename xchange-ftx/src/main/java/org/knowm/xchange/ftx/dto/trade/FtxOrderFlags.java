package org.knowm.xchange.ftx.dto.trade;

import org.knowm.xchange.dto.Order;

public enum FtxOrderFlags implements Order.IOrderFlags {
  POST_ONLY,
  REDUCE_ONLY,
  IOC
}
