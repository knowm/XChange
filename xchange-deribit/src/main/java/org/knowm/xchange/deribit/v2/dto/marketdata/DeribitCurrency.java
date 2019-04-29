package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitCurrency {

  @JsonProperty("coin_type")
  public String coinType;

  @JsonProperty("currency")
  public String currency;

  @JsonProperty("currency_long")
  public String currencyLong;

  @JsonProperty("disabled_deposit_address_creation")
  public boolean disabledDepositAddressCreation;

  @JsonProperty("fee_precision")
  public int feePrecision;

  @JsonProperty("min_confirmations")
  public int minConfirmations;

  @JsonProperty("min_withdrawal_fee")
  public BigDecimal minWithdrawalFee;

  @JsonProperty("withdrawal_fee")
  public BigDecimal withdrawalFee;

  @JsonProperty("withdrawal_priorities")
  public List<DeribitWithdrawalPriority> withdrawalPriorities = null;

  public String getCoinType() {
    return coinType;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCurrencyLong() {
    return currencyLong;
  }

  public boolean isDisabledDepositAddressCreation() {
    return disabledDepositAddressCreation;
  }

  public int getFeePrecision() {
    return feePrecision;
  }

  public int getMinConfirmations() {
    return minConfirmations;
  }

  public BigDecimal getMinWithdrawalFee() {
    return minWithdrawalFee;
  }

  public BigDecimal getWithdrawalFee() {
    return withdrawalFee;
  }

  public List<DeribitWithdrawalPriority> getWithdrawalPriorities() {
    return withdrawalPriorities;
  }
}
