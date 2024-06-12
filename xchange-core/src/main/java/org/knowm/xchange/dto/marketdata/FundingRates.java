package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FundingRates {

  private final List<FundingRate> fundingRates;

  public FundingRates(@JsonProperty("fundingRates") List<FundingRate> fundingRates) {
    this.fundingRates = fundingRates;
  }
}
