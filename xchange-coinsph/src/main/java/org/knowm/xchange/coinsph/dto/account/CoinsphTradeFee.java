package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoinsphTradeFee {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("makerCommission")
  private BigDecimal makerCommission; // e.g., 0.001 for 0.1%

  @JsonProperty("takerCommission")
  private BigDecimal takerCommission; // e.g., 0.001 for 0.1%
}
