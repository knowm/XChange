package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class PoloniexWithdrawal {

  private final long withdrawalNumber;
  private final String currency;
  private final String address;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final Date timestamp;
  private final String status;
  private final String ipAddress;
  private final String paymentID;

  public PoloniexWithdrawal(
      @JsonProperty("withdrawalNumber") long withdrawalNumber,
      @JsonProperty("currency") String currency,
      @JsonProperty("address") String address,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("status") String status,
      @JsonProperty("ipAddress") String ipAddress,
      @JsonProperty("paymentID") String paymentID) {
    super();
    this.withdrawalNumber = withdrawalNumber;
    this.currency = currency;
    this.address = address;
    this.amount = amount;
    this.fee = fee;
    this.timestamp = new Date(timestamp * 1000);
    this.status = status;
    this.ipAddress = ipAddress;
    this.paymentID = paymentID;
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

  public BigDecimal getFee() {
    return fee;
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

  public String getPaymentID() {
    return paymentID;
  }

  @Override
  public String toString() {
    return "PoloniexWithdrawal [withdrawalNumber="
        + withdrawalNumber
        + ", currency="
        + currency
        + ", address="
        + address
        + ", amount="
        + amount
        + ", fee="
        + fee
        + ", timestamp="
        + timestamp
        + ", status="
        + status
        + ", ipAddress="
        + ipAddress
        + ", paymentID="
        + paymentID
        + "]";
  }
}
