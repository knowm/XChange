package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Security Mode DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalSecurityMode {

  /** Security mode type */
  private final String type;

  /** Security mode name */
  private final String name;

  /** From amount */
  private final BigDecimal fromAmount;

  /** To amount */
  private final BigDecimal toAmount;
}
