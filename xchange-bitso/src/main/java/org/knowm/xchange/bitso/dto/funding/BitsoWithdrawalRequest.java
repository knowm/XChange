package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Request DTO Used to create withdrawal requests */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalRequest {

  /** Currency code */
  String currency;

  /** Amount to withdraw */
  BigDecimal amount;

  BigDecimal maxFee;

  String asset;

  String network;

  String method;

  String protocol;

  /** Destination address for crypto withdrawals */
  String address;

  /** Address tag for currencies that require it */
  String addressTag;

  String originId;

  /** Receiving account ID for fiat withdrawals */
  String receivingAccountId;

  /** PIX key for PIX withdrawals */
  String pixKey;

  /** PIX key type (cpf, cnpj, email, phone, random) */
  String pixKeyType;

  /** Description for PIX withdrawals */
  String description;

  /** Internal reference ID */
  String internalId;

  /** Additional parameters for specific withdrawal types */
  Map<String, Object> additionalInfo;

  /** Notes or reference for the withdrawal */
  String notesRef;

  String beneficiary;

  String clabe;

  String institutionCode;
}
