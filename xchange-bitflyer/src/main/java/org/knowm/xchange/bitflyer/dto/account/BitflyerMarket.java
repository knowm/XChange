package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"product_code", "alias"})
public class BitflyerMarket {
  @JsonProperty("product_code")
  private String productCode;

  @JsonProperty("alias")
  private String alias;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  @Override
  public String toString() {
    return "BitflyerMarket{"
        + "productCode='"
        + productCode
        + '\''
        + ", alias='"
        + alias
        + '\''
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
