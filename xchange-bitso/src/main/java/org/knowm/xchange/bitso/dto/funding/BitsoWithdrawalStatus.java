package org.knowm.xchange.bitso.dto.funding;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Status DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalStatus {

  /** Status type */
  private final String type;

  /** Status description */
  private final String description;
}
