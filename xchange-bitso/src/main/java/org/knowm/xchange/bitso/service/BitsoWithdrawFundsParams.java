package org.knowm.xchange.bitso.service;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;

/**
 * Bitso-specific parameters for withdrawing funds Supports both crypto and fiat withdrawals with
 * Bitso's funding API
 */
@Value
@SuperBuilder
public class BitsoWithdrawFundsParams extends DefaultWithdrawFundsParams {

  /** Network for crypto withdrawals (e.g., "BTC", "ETH", "POLYGON") */
  String network;

  /** Whether this is a fiat withdrawal */
  boolean fiatWithdrawal;

  /** For fiat withdrawals - receiving account information */
  Map<String, Object> receivingAccountInfo;

  /** For fiat withdrawals - beneficiary information */
  Map<String, Object> beneficiaryInfo;

  /** For specific payment methods (e.g., PIX key for Brazil) */
  String paymentMethodInfo;

  /** Cross-border payment destination country */
  String destinationCountry;

  /** Optional user reference for the transaction */
  String userReference;

  /** Custom parameters for specific withdrawal types */
  Map<String, Object> customParameters;

  // Convenience constructors for crypto withdrawals
  public BitsoWithdrawFundsParams(Currency currency, BigDecimal amount, String address) {
    super(address, currency, amount);
    this.network = currency.getCurrencyCode();
    this.fiatWithdrawal = false;
    this.receivingAccountInfo = null;
    this.beneficiaryInfo = null;
    this.paymentMethodInfo = null;
    this.destinationCountry = null;
    this.userReference = null;
    this.customParameters = null;
  }

  public BitsoWithdrawFundsParams(
      Currency currency, BigDecimal amount, String address, String network) {
    super(address, currency, amount);
    this.network = network;
    this.fiatWithdrawal = false;
    this.receivingAccountInfo = null;
    this.beneficiaryInfo = null;
    this.paymentMethodInfo = null;
    this.destinationCountry = null;
    this.userReference = null;
    this.customParameters = null;
  }

  public BitsoWithdrawFundsParams(
      Currency currency, BigDecimal amount, String address, String network, String addressTag) {
    super(address, addressTag, currency, amount, null);
    this.network = network;
    this.fiatWithdrawal = false;
    this.receivingAccountInfo = null;
    this.beneficiaryInfo = null;
    this.paymentMethodInfo = null;
    this.destinationCountry = null;
    this.userReference = null;
    this.customParameters = null;
  }
}
