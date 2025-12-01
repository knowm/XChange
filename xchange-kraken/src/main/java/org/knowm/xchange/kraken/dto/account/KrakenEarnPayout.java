package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class KrakenEarnPayout {
  @JsonProperty("accumulated_reward")
  private final KrakenEarnAmount accumulatedReward;

  @JsonProperty("estimated_reward")
  private final KrakenEarnAmount estimatedReward;

  @JsonProperty("period_start")
  private final Date periodStart;

  @JsonProperty("period_end")
  private final Date periodEnd;
}
