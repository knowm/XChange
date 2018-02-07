package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.dto.Order.OrderType;

public interface CancelOrderByOrderTypeParams extends CancelOrderParams {
  OrderType getOrderType();
}
