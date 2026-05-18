package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;

@Getter
@ToString
public class OkxFundingRateHistory {

  private final String instType;
  private final Instrument instrument;
  private final BigDecimal predictedFundingRate;
  private final BigDecimal fundingRate;
  private final Instant fundingTime;
  private final String method;

  public OkxFundingRateHistory(@JsonProperty("instType") String instType,
      @JsonProperty("instId") String instrument,
      @JsonProperty("fundingRate") BigDecimal predictedFundingRate,
      @JsonProperty("realizedRate") BigDecimal fundingRate,
      @JsonProperty("fundingTime") long fundingTime,
      @JsonProperty("method") String method) {
    this.instType = instType;
    this.instrument = OkexAdapters.adaptOkexInstrumentId(instrument);
    this.predictedFundingRate = predictedFundingRate;
    this.fundingRate = fundingRate;
    this.fundingTime = Instant.ofEpochMilli(fundingTime);
    this.method = method;
  }

}
