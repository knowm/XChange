package org.knowm.xchange.bittrex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexBalance {

  private BigDecimal available;
  private BigDecimal balance;
  private String cryptoAddress;
  private String currency;
  private BigDecimal pending;
  private boolean requested;
  private String uuid;

  public BittrexBalance(
      @JsonProperty("Available") BigDecimal available,
      @JsonProperty("Balance") BigDecimal balance,
      @JsonProperty("CryptoAddress") String cryptoAddress,
      @JsonProperty("Currency") String currency,
      @JsonProperty("Pending") BigDecimal pending,
      @JsonProperty("Requested") boolean requested,
      @JsonProperty("Uuid") String uuid) {

    super();
    this.available = available;
    this.balance = balance;
    this.cryptoAddress = cryptoAddress;
    this.currency = currency;
    this.pending = pending;
    this.requested = requested;
    this.uuid = uuid;
  }

  public BigDecimal getAvailable() {

    return available;
  }

  public void setAvailable(BigDecimal available) {

    this.available = available;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  public void setBalance(BigDecimal balance) {

    this.balance = balance;
  }

  public String getCryptoAddress() {

    return cryptoAddress;
  }

  public void setCryptoAddress(String cryptoAddress) {

    this.cryptoAddress = cryptoAddress;
  }

  public String getCurrency() {

    return currency;
  }

  public void setCurrency(String currency) {

    this.currency = currency;
  }

  public BigDecimal getPending() {

    return pending;
  }

  public void setPending(BigDecimal pending) {

    this.pending = pending;
  }

  public boolean isRequested() {

    return requested;
  }

  public void setRequested(boolean requested) {

    this.requested = requested;
  }

  public String getUuid() {

    return uuid;
  }

  public void setUuid(String uuid) {

    this.uuid = uuid;
  }

  @Override
  public String toString() {

    return "Bittrexbalance [available="
        + available
        + ", balance="
        + balance
        + ", cryptoAddress="
        + cryptoAddress
        + ", currency="
        + currency
        + ", pending="
        + pending
        + ", requested="
        + requested
        + ", uuid="
        + uuid
        + "]";
  }
}
