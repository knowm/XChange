package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Mikhail Wall */
public class DSXTransactionStatus {

  private final long id;
  private final long timestamp;
  private final Type type;
  private final BigDecimal amount;
  private final String currency;
  private final long confirmationsCount;
  private final String address;
  private final Status status;
  private final BigDecimal commission;
  private final String txId;

  public DSXTransactionStatus(
      @JsonProperty("id") long id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("type") Type type,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("confirmationsCount") long confirmationsCount,
      @JsonProperty("address") String address,
      @JsonProperty("status") Status status,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("txid") String txId) {

    this.id = id;
    this.timestamp = timestamp;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.confirmationsCount = confirmationsCount;
    this.address = address;
    this.status = status;
    this.commission = commission;
    this.txId = txId;
  }

  public long getId() {

    return id;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public Type getType() {

    return type;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getCurrency() {

    return currency;
  }

  public long getConfirmationsCount() {

    return confirmationsCount;
  }

  public String getAddress() {

    return address;
  }

  public Status getStatus() {

    return status;
  }

  public BigDecimal getCommission() {

    return commission;
  }

  public String getTxId() {

    return txId;
  }

  @Override
  public String toString() {
    return "DSXTransactionStatus{"
        + "id="
        + id
        + ", timestamp="
        + timestamp
        + ", type="
        + type
        + ", amount="
        + amount
        + ", currency='"
        + currency
        + '\''
        + ", confirmationsCount="
        + confirmationsCount
        + ", address='"
        + address
        + '\''
        + ", status="
        + status
        + ", commission="
        + commission
        + ", txid="
        + txId
        + '}';
  }

  public enum Type {
    Withdraw,
    Incoming
  }

  public enum Status {
    reserved0,
    Failed,
    Completed,
    Processing,
    Rejected
  }
}
