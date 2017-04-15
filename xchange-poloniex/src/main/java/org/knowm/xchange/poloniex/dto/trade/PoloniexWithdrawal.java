package org.knowm.xchange.poloniex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PoloniexWithdrawal {

  private final long withdrawalNumber;
  private final String currency;
  private final String address;
  private final BigDecimal amount;
  private final Date timestamp;
  private final String status;
  private final String ipAddress;

  public PoloniexWithdrawal(@JsonProperty("withdrawalNumber") long withdrawalNumber, @JsonProperty("currency") String currency,
      @JsonProperty("address") String address, @JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") long timestamp,
      @JsonProperty("status") String status, @JsonProperty("ipAddress") String ipAddress) {
    super();
    this.withdrawalNumber = withdrawalNumber;
    this.currency = currency;
    this.address = address;
    this.amount = amount;
    this.timestamp = new Date(timestamp * 1000);
    this.status = status;
    this.ipAddress = ipAddress;
  }

  public long getWithdrawalNumber() {
    return withdrawalNumber;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAddress() {
    return address;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getStatus() {
    return status;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  @Override
  public String toString() {
    return "PoloniexWithdrawal [withdrawalNumber=" + withdrawalNumber + ", currency=" + currency + ", address=" + address + ", amount=" + amount
        + ", timestamp=" + timestamp + ", status=" + status + ", ipAddress=" + ipAddress + "]";
  }

}
