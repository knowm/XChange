package org.knowm.xchange.bitso.dto.funding;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Security DTO */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalSecurity {

  /** List of security modes */
  private final List<BitsoWithdrawalSecurityMode> modes;
}
