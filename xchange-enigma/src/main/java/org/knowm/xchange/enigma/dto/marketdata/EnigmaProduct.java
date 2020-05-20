package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnigmaProduct {
  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("product_name")
  private String productName;
}
