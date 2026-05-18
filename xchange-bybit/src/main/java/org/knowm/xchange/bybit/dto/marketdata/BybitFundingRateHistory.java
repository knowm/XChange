package org.knowm.xchange.bybit.dto.marketdata;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import org.knowm.xchange.instrument.Instrument;

@Getter
public class BybitFundingRateHistory {

  private final Instrument instrument;

  private final BigDecimal fundingRate;

  private final Instant fundingRateTimestamp;

  public BybitFundingRateHistory(Instrument instrument, BigDecimal fundingRate, Instant fundingRateTimestamp) {
    this.instrument = instrument;
    this.fundingRate = fundingRate;
    this.fundingRateTimestamp = fundingRateTimestamp;
  }
}
