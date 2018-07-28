package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.dto.trade.LimitOrder;

public class DefaultOpenOrdersParam implements OpenOrdersParams {

  public DefaultOpenOrdersParam() {}

  @Override
  public boolean accept(LimitOrder order) {
    return order != null;
  }
}
