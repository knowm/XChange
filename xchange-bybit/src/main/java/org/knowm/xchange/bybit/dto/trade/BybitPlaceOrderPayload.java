package org.knowm.xchange.bybit.dto.trade;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class BybitPlaceOrderPayload {

  private String category;
  private String symbol;
  private String side;
  private String orderType;
  private String qty;
  private String orderLinkId;
  private String price;

  public BybitPlaceOrderPayload(
      String category,
      String symbol,
      String side,
      String orderType,
      BigDecimal qty,
      String orderLinkId) {
    this.category = category;
    this.symbol = symbol;
    this.side = side;
    this.orderType = orderType;
    this.qty = qty.toString();
    this.orderLinkId = orderLinkId;
  }

  public BybitPlaceOrderPayload(
      String category,
      String symbol,
      String side,
      String orderType,
      BigDecimal qty,
      String orderLinkId,
      BigDecimal price) {
    this.category = category;
    this.symbol = symbol;
    this.side = side;
    this.orderType = orderType;
    this.qty = qty.toString();
    this.orderLinkId = orderLinkId;
    this.price = price.toString();
  }
}
