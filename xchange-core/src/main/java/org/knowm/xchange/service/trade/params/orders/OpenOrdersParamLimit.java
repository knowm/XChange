package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.dto.trade.LimitOrder;

public interface OpenOrdersParamLimit extends OpenOrdersParams {
  @Override
  default boolean accept(LimitOrder order) {
    return order != null;
  }

  Integer getLimit();

  void setLimit(Integer limit);
}
