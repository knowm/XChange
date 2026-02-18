package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class KrakenEarnAllocations {
  @JsonProperty("converted_asset")
  private final String convertedAsset;

  @JsonProperty("items")
  private final List<KrakenEarnAllocation> items;

  @JsonProperty("total_allocated")
  private final BigDecimal totalAllocated;

  @JsonProperty("total_rewarded")
  private final BigDecimal totalRewarded;
}
