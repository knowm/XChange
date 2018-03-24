package org.knowm.xchange.abucoins.dto;

import java.math.BigDecimal;

import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;

public class PlaceOrderRequest extends AbucoinsRequest {
  public final AbucoinsOrder.Type type;
  public final BigDecimal price;
  public final BigDecimal amount;

  public PlaceOrderRequest(AbucoinsOrder.Type type, BigDecimal price, BigDecimal amount) {
    this.type = type;
    this.price = price;
    this.amount = amount;
  }
}
