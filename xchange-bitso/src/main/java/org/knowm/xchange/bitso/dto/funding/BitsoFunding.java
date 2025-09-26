package org.knowm.xchange.bitso.dto.funding;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Funding Transaction DTO Represents a deposit transaction */
@Value
@Builder
@Jacksonized
public class BitsoFunding {

  /** Funding ID */
  private final String fundingId;

  /** Currency code */
  private final String currency;

  /** Funding method (BTC, ETH, SPEI, PIX, etc.) */
  private final String method;

  /** Amount funded */
  private final BigDecimal amount;

  /** Status (pending, complete, cancelled, failed) */
  private final BitsoFundingStatus status;

  /** Creation timestamp */
  private final Date createdAt;

  /** Network for crypto funding */
  private final String network;

  /** Transaction hash for crypto funding */
  private final String txHash;

  /** Number of confirmations for crypto funding */
  private final Integer confirmations;

  /** Required confirmations for completion */
  private final Integer confirmationsRequired;

  /** Fee charged for the funding */
  private final BigDecimal fee;

  /** Additional details specific to the funding method */
  private final Map<String, Object> details;

  /** Internal reference ID */
  private final String internalId;

  /** External reference ID */
  private final String externalId;
}
