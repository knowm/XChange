package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;

public final class EnigmaNewOrderRequest {

  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("side_id")
  private int sideId;

  @JsonInclude(Include.NON_NULL)
  @JsonProperty("quantity")
  private BigDecimal quantity;

  @JsonInclude(Include.NON_NULL)
  @JsonProperty("nominal")
  private BigDecimal nominal;

  @JsonProperty("infra")
  private String infrastructure;

  public EnigmaNewOrderRequest(int productId, int sideId, BigDecimal quantity, BigDecimal nominal,
      String infrastructure) {
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
