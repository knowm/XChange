package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
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

  /** Wallet health */
  @JsonProperty("wallet_health")
  private WalletHealth walletHealth;

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
    this(scale, withdrawalFee, minWithdrawalAmount, WalletHealth.UNKNOWN);
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
      @JsonProperty("wallet_health") WalletHealth walletHealth) {
    this.scale = scale;
    this.withdrawalFee = withdrawalFee;
    this.minWithdrawalAmount = minWithdrawalAmount;
    this.walletHealth = walletHealth;
  }

}
