package org.knowm.xchange.coingi.dto.request;

import java.math.BigDecimal;

public class WithdrawalRequest extends AuthenticatedRequest {
  private String currency;
  private BigDecimal amount;
  private String address;

  public String getCurrency() {
    return currency;
  }

  public WithdrawalRequest setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public WithdrawalRequest setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public WithdrawalRequest setAddress(String address) {
    this.address = address;
    return this;
  }
}
