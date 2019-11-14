package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class EnigmaLimitOrderRequest {

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

  @JsonInclude(Include.NON_NULL)
  @JsonProperty("limitPrice")
  private BigDecimal limitPrice;

  @JsonProperty("infra")
  private String infrastructure;
}
