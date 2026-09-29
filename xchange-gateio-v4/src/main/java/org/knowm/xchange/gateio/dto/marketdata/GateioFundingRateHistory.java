package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@ToString
public class GateioFundingRateHistory {

  private final Instant timestamp;
  private final BigDecimal rate;

  public GateioFundingRateHistory(
      @JsonProperty("t") long timestamp,
      @JsonProperty("r") BigDecimal rate) {
    this.timestamp = Instant.ofEpochSecond(timestamp);
    this.rate = rate;
  }

}
