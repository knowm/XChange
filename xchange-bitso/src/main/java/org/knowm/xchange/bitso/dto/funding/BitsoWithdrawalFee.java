package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Fee DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalFee {

  /** Fee amount */
  private final BigDecimal amount;

  /** Fee type (fixed, percentage) */
  private final BitsoWithdrawalFeeType type;
}
