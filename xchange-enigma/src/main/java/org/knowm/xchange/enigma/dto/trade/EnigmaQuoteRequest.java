package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class EnigmaQuoteRequest {
  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("side_id")
  private int sideId;

  @JsonProperty private BigDecimal quantity;

  public EnigmaQuoteRequest(int productId, int sideId, BigDecimal quantity) {
    this.productId = productId;
    this.sideId = sideId;
    this.quantity = quantity;
  }

  public int getProductId() {
    return productId;
  }

  public int getSideId() {
    return sideId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }
}
