package org.knowm.xchange.bitso.dto.funding;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Tag DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalTag {

  /** Tag title */
  private final String title;

  /** Tag type */
  private final String type;
}
