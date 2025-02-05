package org.knowm.xchange.bybit.dto.trade;

import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelAllOrders;

@Getter
public class BybitCancelAllOrdersParams implements CancelAllOrders {

  private final BybitCategory category;
  private final Instrument symbol;
  private String baseCoin;
  private String settleCoin;
  private String orderFilter;
  private String stopOrderType;

  public BybitCancelAllOrdersParams(BybitCategory category, Instrument symbol) {
    this.category = category;
    this.symbol = symbol;
  }

  public BybitCancelAllOrdersParams(BybitCategory category, Instrument symbol, String baseCoin,
      String settleCoin, String orderFilter, String stopOrderType) {
    this.category = category;
    this.symbol = symbol;
    this.baseCoin = baseCoin;
    this.settleCoin = settleCoin;
    this.orderFilter = orderFilter;
    this.stopOrderType = stopOrderType;
  }

  @Override
  public String toString() {
    return "BybitCancelAllOrdersParams{" +
        "category=" + category +
        ", symbol=" + symbol +
        ", baseCoin='" + baseCoin + '\'' +
        ", settleCoin='" + settleCoin + '\'' +
        ", orderFilter='" + orderFilter + '\'' +
        ", stopOrderType='" + stopOrderType + '\'' +
        '}';
  }
}

