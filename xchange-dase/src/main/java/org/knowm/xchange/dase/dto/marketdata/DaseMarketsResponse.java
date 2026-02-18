package org.knowm.xchange.dase.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DaseMarketsResponse {
  @JsonProperty("markets")
  public List<DaseMarketConfig> markets;
}
