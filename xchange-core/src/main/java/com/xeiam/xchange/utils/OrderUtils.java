package com.xeiam.xchange.utils;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderRequirements;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;

public final class OrderUtils {
  public static boolean verify(LimitOrder order, OrderRequirements reqs) {

    boolean orderOk = verify((Order) order, reqs);
    return orderOk && order.getLimitPrice().scale() == reqs.getPriceScale();
  }

  public static boolean verify(Order order, OrderRequirements reqs) {

    BigDecimal amount = order.getTradableAmount();
    return amount.scale() == reqs.getAmountScale() && amount.compareTo(reqs.getAmountMinimum()) >= 0;
  }

  private OrderUtils() {
  }
}
