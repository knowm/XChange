package org.knowm.xchange.huobi.dto.account;

import java.math.BigDecimal;

public class HuobiBalanceSum {

  private BigDecimal available;
  private BigDecimal frozen;

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getFrozen() {
    return frozen;
  }

  public BigDecimal getTotal() {
    return available.add(frozen);
  }

  public void setAvailable(BigDecimal available) {
    this.available = available;
  }

  public void setFrozen(BigDecimal frozen) {
    this.frozen = frozen;
  }
}
