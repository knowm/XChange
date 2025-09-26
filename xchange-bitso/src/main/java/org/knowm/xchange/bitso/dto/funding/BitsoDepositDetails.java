package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso Deposit Details DTO Contains information about deposit addresses and payment instructions
 */
@Value
@Builder
@Jacksonized
public class BitsoDepositDetails {

  /** Currency code */
  private final String currency;

  /** Network for crypto deposits */
  private final String network;

  /** Deposit address for crypto */
  private final String address;

  /** Address tag for currencies that require it */
  private final String addressTag;

  /** Minimum deposit amount */
  private final BigDecimal minimumAmount;

  /** Maximum deposit amount */
  private final BigDecimal maximumAmount;

  /** Deposit fee */
  private final BigDecimal fee;

  /** Payment instructions for fiat deposits */
  private final String paymentInstructions;

  /** CLABE number for MXN deposits */
  private final String clabe;

  /** Bank name for fiat deposits */
  private final String bankName;

  /** Account holder name */
  private final String accountHolderName;

  /** Additional deposit information */
  private final String notes;
}
