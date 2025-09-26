package org.knowm.xchange.bitso.dto.funding;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Icon Configuration DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalIconConfig {

  /** Icon path */
  private final String path;

  /** Icon name */
  private final String name;
}
