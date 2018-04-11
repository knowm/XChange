package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class CurrencyMetaData implements Serializable {

  @JsonProperty("scale")
  private final int scale;

  /** Withdrawal fee */
  @JsonProperty("withdrawal_fee")
  private BigDecimal withdrawalFee;

  /**
   * Constructor
   *
   * @param scale
   */
  public CurrencyMetaData(
      @JsonProperty("scale") int scale, @JsonProperty("withdrawal_fee") BigDecimal withdrawalFee) {
    this.scale = scale;
    this.withdrawalFee = withdrawalFee;
  }

  public int getScale() {
    return scale;
  }

  public BigDecimal getWithdrawalFee() {
    return withdrawalFee;
  }

  @Override
  public String toString() {
    return "CurrencyMetaData [" + "scale=" + scale + ", withdrawalFee=" + withdrawalFee + "]";
  }
}
