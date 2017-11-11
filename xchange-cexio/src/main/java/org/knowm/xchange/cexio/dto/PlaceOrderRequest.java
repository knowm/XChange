package org.knowm.xchange.cexio.dto;

import org.knowm.xchange.cexio.dto.trade.CexIOOrder;

import java.math.BigDecimal;

public class PlaceOrderRequest extends CexIORequest {
  public final CexIOOrder.Type type;
  public final BigDecimal price;
  public final BigDecimal amount;

  public PlaceOrderRequest(CexIOOrder.Type type, BigDecimal price, BigDecimal amount) {
    this.type = type;
    this.price = price;
    this.amount = amount;
  }
}
