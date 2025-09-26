package org.knowm.xchange.bitso.dto.funding;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Transaction DTO Represents a withdrawal transaction */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawal {

  /** Withdrawal ID */
  @JsonProperty("wid")
  String withdrawalId;

  /** Currency code */
  String currency;

  /** Withdrawal method (BTC, ETH, SPEI, PIX, etc.) */
  String method;

  /** Amount withdrawn */
  BigDecimal amount;

  /** Status (pending, complete, cancelled, failed) */
  String status;

  /** Creation timestamp */
  Date createdAt;

  /** Network for crypto withdrawal */
  String network;

  /** Destination address for crypto withdrawal */
  String address;

  /** Address tag for currencies that require it */
  String addressTag;

  /** Transaction hash for crypto withdrawal */
  String txHash;

  /** Number of confirmations for crypto withdrawal */
  Integer confirmations;

  /** Fee charged for the withdrawal */
  BigDecimal fee;

  /** Additional details specific to the withdrawal method */
  Map<String, Object> details;

  /** Internal reference ID */
  String internalId;

  /** External reference ID */
  String externalId;

  /** Receiving account ID for fiat withdrawals */
  String receivingAccountId;

  /** PIX key for PIX withdrawals */
  String pixKey;

  /** CLABE for Mexican peso withdrawals */
  String clabe;

  /** Bank name for fiat withdrawals */
  String bankName;

  /** Account holder name for fiat withdrawals */
  String accountHolderName;
}
