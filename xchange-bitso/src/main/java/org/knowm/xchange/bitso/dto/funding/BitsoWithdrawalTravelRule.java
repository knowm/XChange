package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Travel Rule DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalTravelRule {

  /** Lower threshold for travel rule */
  private final BigDecimal lowerThreshold;
}
