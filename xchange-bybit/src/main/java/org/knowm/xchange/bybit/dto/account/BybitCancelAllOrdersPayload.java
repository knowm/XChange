package org.knowm.xchange.bybit.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.BybitCategory;

@Getter
@Setter
public class BybitCancelAllOrdersPayload {
  String category;
  String symbol;
  String baseCoin;
  String settleCoin;
  String orderFilter;
  String stopOrderType;

  public BybitCancelAllOrdersPayload(String category, String symbol, String baseCoin,
      String settleCoin, String orderFilter, String stopOrderType) {
    this.category = category;
    this.symbol = symbol;
    this.baseCoin = baseCoin;
    this.settleCoin = settleCoin;
    this.orderFilter = orderFilter;
    this.stopOrderType = stopOrderType;
  }
}
