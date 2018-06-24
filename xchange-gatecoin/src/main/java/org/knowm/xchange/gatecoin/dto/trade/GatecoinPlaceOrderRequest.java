package org.knowm.xchange.gatecoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GatecoinPlaceOrderRequest {
  @JsonProperty("Amount")
  private BigDecimal amount;

  @JsonProperty("Price")
  private BigDecimal price;

  @JsonProperty("Way")
  private String way;

  @JsonProperty("Code")
  private String code;

  public GatecoinPlaceOrderRequest(BigDecimal amount, BigDecimal price, String way, String code) {
    this.amount = amount;
    this.price = price;
    this.way = way;
    this.code = code;
  }
}
