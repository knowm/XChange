package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class CurrencyMetaData implements Serializable {

  private static final long serialVersionUID = -247899067657358542L;

  @JsonProperty("scale")
  private final Integer scale;

  /** Withdrawal fee */
  @JsonProperty("withdrawal_fee")
  private final BigDecimal withdrawalFee;

  /** Minimum withdrawal amount */
  @JsonProperty("min_withdrawal_amount")
  private final BigDecimal minWithdrawalAmount;

  /** Deposit enabled */
  @JsonProperty("deposit_enabled")
  private boolean depositEnabled;

  /** Withdraw enabled */
  @JsonProperty("withdraw_enabled")
  private boolean withdrawEnabled;

  /**
   * Constructor
   *
   * @param scale
   * @param withdrawalFee
   */
  public CurrencyMetaData(Integer scale, BigDecimal withdrawalFee) {
    this(scale, withdrawalFee, null);
  }

  /**
   * Constructor
   *
   * @param scale
   * @param withdrawalFee
   * @param minWithdrawalAmount
   */
  public CurrencyMetaData(Integer scale, BigDecimal withdrawalFee, BigDecimal minWithdrawalAmount) {
    this(scale, withdrawalFee, minWithdrawalAmount, true, true);
  }

  /**
   * Constructor
   *
   * @param scale
   */
  public CurrencyMetaData(
      @JsonProperty("scale") Integer scale,
      @JsonProperty("withdrawal_fee") BigDecimal withdrawalFee,
      @JsonProperty("min_withdrawal_amount") BigDecimal minWithdrawalAmount,
      @JsonProperty("deposit_enabled") boolean depositEnabled,
      @JsonProperty("withdraw_enabled") boolean withdrawEnabled) {
    this.scale = scale;
    this.withdrawalFee = withdrawalFee;
    this.minWithdrawalAmount = minWithdrawalAmount;
    this.depositEnabled = depositEnabled;
    this.withdrawEnabled = withdrawEnabled;
  }

  public Integer getScale() {
    return scale;
  }

  public BigDecimal getWithdrawalFee() {
    return withdrawalFee;
  }

  public BigDecimal getMinWithdrawalAmount() {
    return minWithdrawalAmount;
  }

  public boolean isDepositEnabled() {
    return depositEnabled;
  }

  public boolean isWithdrawEnabled() {
    return withdrawEnabled;
  }

  @Override
  public String toString() {
    return "CurrencyMetaData ["
        + "scale="
        + scale
        + ", withdrawalFee="
        + withdrawalFee
        + ", minWithdrawalAmount="
        + minWithdrawalAmount
        + ", depositEnabled="
        + depositEnabled
        + ", withdrawEnabled="
        + withdrawEnabled
        + "]";
  }
}
