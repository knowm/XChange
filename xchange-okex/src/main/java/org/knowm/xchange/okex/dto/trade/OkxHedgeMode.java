package org.knowm.xchange.okex.dto.trade;

import org.knowm.xchange.dto.Order;

public enum OkxHedgeMode implements Order.IOrderFlags {
  ONEWAY,
  TWOWAY;
}
