package org.knowm.xchange.cexio.dto;

import java.math.BigDecimal;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;

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
