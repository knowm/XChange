package com.xeiam.xchange.bittrex.v1.dto.account;

import java.math.BigDecimal;

public class BittrexBalance {

  private BigDecimal Available;
  private BigDecimal Balance;
  private String CryptoAddress;
  private String Currency;
  private BigDecimal Pending;
  private boolean Requested;
  private String Uuid;

  public BigDecimal getAvailable() {

    return this.Available;
  }

  public void setAvailable(BigDecimal Available) {

    this.Available = Available;
  }

  public BigDecimal getBalance() {

    return this.Balance;
  }

  public void setBalance(BigDecimal Balance) {

    this.Balance = Balance;
  }

  public String getCryptoAddress() {

    return this.CryptoAddress;
  }

  public void setCryptoAddress(String CryptoAddress) {

    this.CryptoAddress = CryptoAddress;
  }

  public String getCurrency() {

    return this.Currency;
  }

  public void setCurrency(String Currency) {

    this.Currency = Currency;
  }

  public BigDecimal getPending() {

    return this.Pending;
  }

  public void setPending(BigDecimal Pending) {

    this.Pending = Pending;
  }

  public boolean getRequested() {

    return this.Requested;
  }

  public void setRequested(boolean Requested) {

    this.Requested = Requested;
  }

  public String getUuid() {

    return this.Uuid;
  }

  public void setUuid(String Uuid) {

    this.Uuid = Uuid;
  }

  @Override
  public String toString() {

    return "BitfinexBalance [Available=" + Available + ", Balance=" + Balance + ", CryptoAddress=" + CryptoAddress + ", Currency=" + Currency + ", Pending=" + Pending + ", Requested=" + Requested
        + ", Uuid=" + Uuid + "]";
  }

}