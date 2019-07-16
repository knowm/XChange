package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EnigmaProductMarketData {

  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("product_name")
  private String productName;

  @JsonProperty("bid")
  private BigDecimal bid;

  @JsonProperty("ask")
  private BigDecimal ask;
}
