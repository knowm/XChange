package org.knowm.xchange.bitso.dto.account;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso Deposit Address DTO
 *
 * @deprecated This functionality has been moved to the Bitso Funding API. Use funding API endpoints
 *     for deposit address operations.
 */
@Deprecated
@Value
@Builder
@Jacksonized
public class BitsoDepositAddress {

  /** The deposit address */
  private final String depositAddress;

  /** Error message if any */
  private final String error;

  @Override
  public String toString() {
    return "BitsoDepositAddress [depositAddress=" + depositAddress + ", error=" + error + "]";
  }
}
