package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnigmaProduct {
  private int productId;
  private String productName;

  public EnigmaProduct(
      @JsonProperty("product_id") int productId, @JsonProperty("product_name") String productName) {
    this.productId = productId;
    this.productName = productName;
  }

  public int getProductId() {
    return this.productId;
  }

  public String getProductName() {
    return this.productName;
  }
}
