package org.knowm.xchange.ccex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CCEXBalance {

  private String Currency;
  private BigDecimal Balance;
  private BigDecimal Available;
  private BigDecimal Pending;
  private String CryptoAddress;

  public CCEXBalance(
      @JsonProperty("Currency") String currency,
      @JsonProperty("Balance") BigDecimal balance,
      @JsonProperty("Available") BigDecimal available,
      @JsonProperty("Pending") BigDecimal pending,
      @JsonProperty("CryptoAddress") String cryptoAddress) {
    super();
    Currency = currency;
    Balance = balance;
    Available = available;
    Pending = pending;
    CryptoAddress = cryptoAddress;
  }

  public String getCurrency() {
    return Currency;
  }

  public void setCurrency(String currency) {
    Currency = currency;
  }

  public BigDecimal getBalance() {
    return Balance;
  }

  public void setBalance(BigDecimal balance) {
    Balance = balance;
  }

  public BigDecimal getAvailable() {
    return Available;
  }

  public void setAvailable(BigDecimal available) {
    Available = available;
  }

  public BigDecimal getPending() {
    return Pending;
  }

  public void setPending(BigDecimal pending) {
    Pending = pending;
  }

  public String getCryptoAddress() {
    return CryptoAddress;
  }

  public void setCryptoAddress(String cryptoAddress) {
    CryptoAddress = cryptoAddress;
  }

  @Override
  public String toString() {
    return "CCEXBalance [Currency="
        + Currency
        + ", Balance="
        + Balance
        + ", Available="
        + Available
        + ", Pending="
        + Pending
        + ", CryptoAddress="
        + CryptoAddress
        + "]";
  }
}
