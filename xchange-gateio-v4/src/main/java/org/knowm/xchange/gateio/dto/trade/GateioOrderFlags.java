package org.knowm.xchange.gateio.dto.trade;

import lombok.AllArgsConstructor;
import org.knowm.xchange.dto.Order;

@AllArgsConstructor
public class GateioOrderFlags implements Order.IOrderFlags {
  public GateioTimeInForce timeInForce;

}
