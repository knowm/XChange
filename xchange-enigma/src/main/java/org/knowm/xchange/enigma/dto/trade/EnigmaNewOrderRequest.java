package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class EnigmaNewOrderRequest {

  @JsonProperty("product_jd")
  private int productId;

  @JsonProperty("side_id")
  private int sideId;

  @JsonProperty private BigDecimal quantity;

  @JsonProperty private BigDecimal nominal;

  @JsonProperty("infra")
  private String infrastructure;

  public EnigmaNewOrderRequest(
      int productId, int sideId, BigDecimal quantity, BigDecimal nominal, String infrastructure) {
    this.productId = productId;
    this.sideId = sideId;
    this.quantity = quantity;
    this.nominal = nominal;
    this.infrastructure = infrastructure;
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

  public BigDecimal getNominal() {
    return nominal;
  }

  public String getInfrastructure() {
    return infrastructure;
  }
}
