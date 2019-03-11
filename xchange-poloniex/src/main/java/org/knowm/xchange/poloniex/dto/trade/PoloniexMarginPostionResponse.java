package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "amount",
  "total",
  "basePrice",
  "liquidationPrice",
  "pl",
  "lendingFees",
  "type"
})
public class PoloniexMarginPostionResponse {
  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("total")
  private BigDecimal total;

  @JsonProperty("basePrice")
  private BigDecimal basePrice;

  @JsonProperty("liquidationPrice")
  private BigDecimal liquidationPrice;

  @JsonProperty("pl")
  private BigDecimal pl;

  @JsonProperty("lendingFees")
  private BigDecimal lendingFees;

  @JsonProperty("type")
  private String type;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public BigDecimal getLiquidationPrice() {
    return liquidationPrice;
  }

  public BigDecimal getPl() {
    return pl;
  }

  public BigDecimal getLendingFees() {
    return lendingFees;
  }

  public String getType() {
    return type;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  @Override
  public String toString() {
    return "PoloniexMarginPostionResponse{"
        + "amount="
        + amount
        + ", total="
        + total
        + ", basePrice="
        + basePrice
        + ", liquidationPrice="
        + liquidationPrice
        + ", pl="
        + pl
        + ", lendingFees="
        + lendingFees
        + ", type='"
        + type
        + '\''
        + '}';
  }
}
