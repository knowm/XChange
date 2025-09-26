package org.knowm.xchange.bitso.dto.funding;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Bitso Withdrawal Method DTO Represents withdrawal method information for a currency */
@Value
@Builder
@Jacksonized
public class BitsoWithdrawalMethod {

  /** Method identifier */
  private final String method;

  /** Method name/display name */
  private final String name;

  /** Integration identifier */
  private final String integration;

  /** Sub method (can be null) */
  private final String subMethod;

  /** Method display name */
  private final String methodName;

  /** Network name */
  private final String networkName;

  /** Channel name */
  private final String channelName;

  /** Network description */
  private final String networkDescription;

  /** Required fields for this method */
  private final List<String> requiredFields;

  /** Optional fields for this method */
  private final List<String> optionalFields;

  /** List of currency configurations for this withdrawal method */
  private final List<BitsoWithdrawalCurrencyConfiguration> currencyConfigurations;

  /** Consumer contacts enabled flag */
  private final Boolean consumerContactsEnabled;

  /** Method description */
  private final String methodDescription;

  /** Icon configuration */
  private final BitsoWithdrawalIconConfig iconConfig;

  /** List of tags */
  private final List<BitsoWithdrawalTag> tags;

  /** Network identifier */
  private final String network;

  /** Protocol identifier */
  private final String protocol;

  /** Security configuration */
  private final BitsoWithdrawalSecurity security;

  /** Contract type */
  private final String contract;

  /** Taxes list (can be empty) */
  private final List<Object> taxes;

  /** Compliance configuration */
  private final BitsoWithdrawalCompliance compliance;

  /** Payment rails (can be null) */
  private final String paymentRails;
}
