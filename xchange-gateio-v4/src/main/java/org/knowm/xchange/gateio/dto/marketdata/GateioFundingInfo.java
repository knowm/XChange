package org.knowm.xchange.gateio.dto.marketdata;


import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.dto.marketdata.FundingRate;

import java.time.Instant;

@Getter
@Setter
public class GateioFundingInfo {
  private FundingRate.FundingRateInterval funding_interval = FundingRate.FundingRateInterval.H8;
  private Instant funding_next_apply;

  public GateioFundingInfo(Integer interval, Instant next_apply) {
    interval = interval / 60;
    switch (interval) {
      case 1: {
        funding_interval = FundingRate.FundingRateInterval.H1;
        break;
      }
      case 2: {
        funding_interval = FundingRate.FundingRateInterval.H2;
        break;
      }
      case 4: {
        funding_interval = FundingRate.FundingRateInterval.H4;
        break;
      }
      case 6: {
        funding_interval = FundingRate.FundingRateInterval.H6;
        break;
      }
    }
    funding_next_apply = next_apply;
  }
}

