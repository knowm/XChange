package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitCurrency {

  /** The type of the currency. */
  @JsonProperty("coin_type")
  private String coinType;

  /**
   * The abbreviation of the currency. This abbreviation is used elsewhere in the API to identify
   * the currency.
   */
  @JsonProperty("currency")
  private String currency;

  /** The full name for the currency. */
  @JsonProperty("currency_long")
  private String currencyLong;

  /** False if deposit address creation is disabled */
  @JsonProperty("disabled_deposit_address_creation")
  private boolean disabledDepositAddressCreation;

  /** fee precision */
  @JsonProperty("fee_precision")
  private int feePrecision;

  /** Minimum number of block chain confirmations before deposit is accepted. */
  @JsonProperty("min_confirmations")
  private int minConfirmations;

  /** The minimum transaction fee paid for withdrawals */
  @JsonProperty("min_withdrawal_fee")
  private BigDecimal minWithdrawalFee;

  /** The total transaction fee paid for withdrawals */
  @JsonProperty("withdrawal_fee")
  private BigDecimal withdrawalFee;

  @JsonProperty("withdrawal_priorities")
  private List<DeribitWithdrawalPriority> withdrawalPriorities = null;
}
