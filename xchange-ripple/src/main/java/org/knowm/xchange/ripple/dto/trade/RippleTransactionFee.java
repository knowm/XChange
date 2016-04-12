package org.knowm.xchange.ripple.dto.trade;

import java.math.BigDecimal;

public class RippleTransactionFee {
  private BigDecimal fee;
  private boolean success;

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(final BigDecimal fee) {
    this.fee = fee;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(final boolean success) {
    this.success = success;
  }
}
