package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnigmaExecuteQuoteRequest {
  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("side_id")
  private int sideId;

  @JsonProperty("quantity")
  private BigDecimal quantity;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("rfq_client_id")
  private String rfqClientId;

  @JsonProperty("infra")
  private String infra;
}
