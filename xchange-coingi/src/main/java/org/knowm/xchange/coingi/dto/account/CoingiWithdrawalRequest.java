package org.knowm.xchange.coingi.dto.account;

import java.math.BigDecimal;
import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;

public class CoingiWithdrawalRequest extends CoingiAuthenticatedRequest {
  private String currency;
  private BigDecimal amount;
  private String address;

  public String getCurrency() {
    return currency;
  }

  public CoingiWithdrawalRequest setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public CoingiWithdrawalRequest setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public CoingiWithdrawalRequest setAddress(String address) {
    this.address = address;
    return this;
  }
}
