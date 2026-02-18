package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class KrakenEarnAllocation {
  @JsonProperty("amount_allocated")
  private final KrakenEarnAmountAllocated amountAllocated;

  @JsonProperty("native_asset")
  private final String nativeAsset;

  @JsonProperty("payout")
  private final KrakenEarnPayout payout;

  @JsonProperty("strategy_id")
  private final String strategyId;

  @JsonProperty("total_rewarded")
  private final KrakenEarnAmount totalRewarded;
}
