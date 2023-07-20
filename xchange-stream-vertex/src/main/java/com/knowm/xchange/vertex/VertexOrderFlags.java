package com.knowm.xchange.vertex;

import org.knowm.xchange.dto.Order;

public enum VertexOrderFlags implements Order.IOrderFlags {

  TIME_IN_FORCE_IOC,
  TIME_IN_FORCE_GTC,
  TIME_IN_FORCE_FOK,
  TIME_IN_FORCE_POS_ONLY
}
