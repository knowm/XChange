package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BybitFundingRateHistoryRaw {

  private final String instrument;

  private final BigDecimal fundingRate;

  private final Instant fundingRateTimestamp;

  public BybitFundingRateHistoryRaw(@JsonProperty("symbol") String instrument, @JsonProperty("fundingRate") BigDecimal fundingRate, @JsonProperty("fundingRateTimestamp") long fundingRateTimestamp) {
    this.instrument = instrument;
    this.fundingRate = fundingRate;
    this.fundingRateTimestamp = Instant.ofEpochMilli(fundingRateTimestamp);
  }
}
