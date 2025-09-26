package org.knowm.xchange.bitso.dto.funding;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BitsoWithdrawalCompliance {

  /** Travel rule configuration */
  private final BitsoWithdrawalTravelRule travelRule;
}
