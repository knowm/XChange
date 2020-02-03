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

  /**
   * Constructor
   *
   * @param scale
   */
  public CurrencyMetaData(Integer scale, BigDecimal withdrawalFee) {
    this(scale, withdrawalFee, null);
  }

  /**
   * Constructor
   *
   * @param scale
   */
  public CurrencyMetaData(
      @JsonProperty("scale") Integer scale,
      @JsonProperty("withdrawal_fee") BigDecimal withdrawalFee,
      @JsonProperty("min_withdrawal_amount") BigDecimal minWithdrawalAmount) {
    this.scale = scale;
    this.withdrawalFee = withdrawalFee;
    this.minWithdrawalAmount = minWithdrawalAmount;
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

  @Override
  public String toString() {
    return "CurrencyMetaData ["
        + "scale="
        + scale
        + ", withdrawalFee="
        + withdrawalFee
        + ", minWithdrawalAmount="
        + minWithdrawalAmount
        + "]";
  }
}
