package org.knowm.xchange.bitso.dto.funding;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Legal Operating Entity DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalLegalOperatingEntity {

  /** Country code */
  private final String countryCode;

  /** Legal operation entity name */
  private final String legalOperationEntity;
}
