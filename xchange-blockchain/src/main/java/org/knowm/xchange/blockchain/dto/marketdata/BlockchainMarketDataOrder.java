package org.knowm.xchange.blockchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainMarketDataOrder {

  @JsonProperty("px")
  private final BigDecimal price;

  @JsonProperty("qty")
  private final BigDecimal quantity;

  private final Long num;
}
