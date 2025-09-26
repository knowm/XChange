package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Limits DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalLimits {

  /** Status of the limit (upgradeable, not_upgradeable) */
  private final String status;

  /** System maximum limit */
  private final BigDecimal systemMax;

  /** System minimum limit */
  private final BigDecimal systemMin;

  /** Transaction limit */
  private final BigDecimal txLimit;
}
