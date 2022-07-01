package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.dto.trade.LimitOrder;

public interface OpenOrdersParamOffset extends OpenOrdersParams {
  @Override
  default boolean accept(LimitOrder order) {
    return order != null;
  }

  Integer getOffset();

  void setOffset(Integer offset);
}
