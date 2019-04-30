package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitCurrency {

  @JsonProperty("coin_type")
  private String coinType;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("currency_long")
  private String currencyLong;

  @JsonProperty("disabled_deposit_address_creation")
  private boolean disabledDepositAddressCreation;

  @JsonProperty("fee_precision")
  private int feePrecision;

  @JsonProperty("min_confirmations")
  private int minConfirmations;

  @JsonProperty("min_withdrawal_fee")
  private BigDecimal minWithdrawalFee;

  @JsonProperty("withdrawal_fee")
  private BigDecimal withdrawalFee;

  @JsonProperty("withdrawal_priorities")
  private List<DeribitWithdrawalPriority> withdrawalPriorities = null;


  public String getCoinType() {
    return coinType;
  }

  public void setCoinType(String coinType) {
    this.coinType = coinType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrencyLong() {
    return currencyLong;
  }

  public void setCurrencyLong(String currencyLong) {
    this.currencyLong = currencyLong;
  }

  public boolean isDisabledDepositAddressCreation() {
    return disabledDepositAddressCreation;
  }

  public void setDisabledDepositAddressCreation(boolean disabledDepositAddressCreation) {
    this.disabledDepositAddressCreation = disabledDepositAddressCreation;
  }

  public int getFeePrecision() {
    return feePrecision;
  }

  public void setFeePrecision(int feePrecision) {
    this.feePrecision = feePrecision;
  }

  public int getMinConfirmations() {
    return minConfirmations;
  }

  public void setMinConfirmations(int minConfirmations) {
    this.minConfirmations = minConfirmations;
  }

  public BigDecimal getMinWithdrawalFee() {
    return minWithdrawalFee;
  }

  public void setMinWithdrawalFee(BigDecimal minWithdrawalFee) {
    this.minWithdrawalFee = minWithdrawalFee;
  }

  public BigDecimal getWithdrawalFee() {
    return withdrawalFee;
  }

  public void setWithdrawalFee(BigDecimal withdrawalFee) {
    this.withdrawalFee = withdrawalFee;
  }

  public List<DeribitWithdrawalPriority> getWithdrawalPriorities() {
    return withdrawalPriorities;
  }

  public void setWithdrawalPriorities(List<DeribitWithdrawalPriority> withdrawalPriorities) {
    this.withdrawalPriorities = withdrawalPriorities;
  }

  @Override
  public String toString() {
    return "DeribitCurrency{" +
            "coinType='" + coinType + '\'' +
            ", currency='" + currency + '\'' +
            ", currencyLong='" + currencyLong + '\'' +
            ", disabledDepositAddressCreation=" + disabledDepositAddressCreation +
            ", feePrecision=" + feePrecision +
            ", minConfirmations=" + minConfirmations +
            ", minWithdrawalFee=" + minWithdrawalFee +
            ", withdrawalFee=" + withdrawalFee +
            ", withdrawalPriorities=" + withdrawalPriorities +
            '}';
  }
}
