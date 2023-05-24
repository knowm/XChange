package org.knowm.xchange.bitstamp.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BitstampPairInfo {

  @JsonProperty("name")
  String name;

  @JsonProperty("url_symbol")
  String urlSymbol;

  @JsonProperty("base_decimals")
  Integer baseDecimals;

  @JsonProperty("counter_decimals")
  Integer counterDecimals;

  @JsonProperty("minimum_order")
  String minimumOrder;

  @JsonProperty("trading")
  String trading;

  @JsonProperty("description")
  String description;

}
