package org.knowm.xchange.bitso.dto.funding;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Currency Configuration DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalCurrencyConfiguration {

  /** Asset identifier */
  private final String asset;

  /** Currency code */
  private final String currency;

  /** Fee information */
  private final BitsoWithdrawalFee fee;

  /** Legal operating entity information */
  private final BitsoWithdrawalLegalOperatingEntity legalOperatingEntity;

  /** Limits information */
  private final BitsoWithdrawalLimits limits;

  /** Status information */
  private final BitsoWithdrawalStatus status;
}
