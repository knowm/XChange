package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KrakenEarnAllocationsRequest {
  @JsonProperty("nonce")
  Long nonce;

  @JsonProperty("ascending")
  Boolean ascending;

  @JsonProperty("converted_asset")
  String convertedAsset;

  @JsonProperty("hide_zero_allocations")
  Boolean hideZeroAllocations;
}
