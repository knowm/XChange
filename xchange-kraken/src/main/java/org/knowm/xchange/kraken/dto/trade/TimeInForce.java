package org.knowm.xchange.kraken.dto.trade;

import org.knowm.xchange.dto.*;

public enum TimeInForce implements Order.IOrderFlags {
  GTC,
  IOC,
  GTD
}
