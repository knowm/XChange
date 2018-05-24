package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CexioCancelReplaceOrderRequest extends CexIORequest {

  @JsonProperty("order_id")
  public final String orderId;

  @JsonProperty("type")
  public final String type;

  @JsonProperty("amount")
  public final BigDecimal amount;

  @JsonProperty("price")
  public final BigDecimal price;

  public CexioCancelReplaceOrderRequest(
      String orderId, String type, BigDecimal amount, BigDecimal price) {
    this.orderId = orderId;
    this.type = type;
    this.amount = amount;
    this.price = price;
  }
}
