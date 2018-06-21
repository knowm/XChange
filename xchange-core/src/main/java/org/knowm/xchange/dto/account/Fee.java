package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

public final class Fee implements Serializable {
  private final BigDecimal makerFee;
  private final BigDecimal takerFee;

  public Fee(BigDecimal makerFee, BigDecimal takerFee) {
    this.makerFee = makerFee;
    this.takerFee = takerFee;
  }

  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

  @Override
  public String toString() {
    return "Fee [makerFee=" + makerFee + ", takerFee=" + takerFee + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Fee other = (Fee) obj;
    return other.makerFee.equals(makerFee) && other.takerFee.equals(takerFee);
  }

  @Override
  public int hashCode() {
    return makerFee.hashCode() + 31 * takerFee.hashCode();
  }
}
