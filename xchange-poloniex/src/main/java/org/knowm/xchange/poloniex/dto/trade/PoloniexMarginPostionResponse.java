package org.knowm.xchange.poloniex.dto.trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}