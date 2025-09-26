package org.knowm.xchange.bitso.dto.funding;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Receiving Account DTO - Represents a receiving account for fiat withdrawals */
@Value
@Builder
@Jacksonized
public class BitsoReceivingAccount {

  /** Contact ID */
  private final Integer contactId;

  /** User ID */
  private final Integer userId;

  /** Account alias */
  private final String alias;

  /** Account taxonomy information */
  private final Taxonomy taxonomy;

  /** Currency code */
  private final String currency;

  /** Account details */
  private final List<Detail> details;

  /** Account creation timestamp */
  private final Instant created;

  /** Taxonomy information for the account */
  @Value
  @Builder
  @Jacksonized
  public static class Taxonomy {
    /** Network type (e.g., spei) */
    private final String network;

    /** Asset type (e.g., mxn) */
    private final String asset;

    /** Protocol type (e.g., clabe) */
    private final String protocol;

    /** Integration provider */
    private final String integration;
  }

  /** Account detail information */
  @Value
  @Builder
  @Jacksonized
  public static class Detail {
    /** Display name of the detail */
    private final String name;

    /** Key identifier for the detail */
    private final String key;

    /** Value of the detail */
    private final String value;
  }
}
