package org.knowm.xchange.kuna.dto;

import java.math.BigDecimal;

public class KunaAccountItemDto {

  private String currency;

  private BigDecimal balance;

  private BigDecimal locked;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public BigDecimal getLocked() {
    return locked;
  }

  public void setLocked(BigDecimal locked) {
    this.locked = locked;
  }
}
