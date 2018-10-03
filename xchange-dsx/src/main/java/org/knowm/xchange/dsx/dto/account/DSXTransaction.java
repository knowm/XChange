package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** @author Mikhail Wall */
public class DSXTransaction {

  private final long id;
  private final Date timestamp;
  private final Type type;
  private final BigDecimal amount;
  private final String currency;
  private final String address;
  private final Status status;
  private final BigDecimal commission;
  private final String txId;

  public DSXTransaction(
      @JsonProperty("id") long id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("type") Type type,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("address") String address,
      @JsonProperty("status") Status status,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("txid") String txId) {

    this.id = id;
    this.timestamp = new Date(timestamp * 1000);
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.address = address;
    this.status = status;
    this.commission = commission;
    this.txId = txId;
  }

  public long getId() {
    return id;
  }

  public Date getTimestamp() {
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
    return "DSXTransaction [id="
        + id
        + ", timestamp="
        + timestamp
        + ", type="
        + type
        + ", amount="
        + amount
        + ", currency="
        + currency
        + ", address="
        + address
        + ", status="
        + status
        + ", commission="
        + commission
        + ", txId="
        + txId
        + "]";
  }

  public enum Type {
    Withdraw,
    Incoming
  }

  public enum Status {
    Failed(1),
    Completed(2),
    Processing(3),
    Rejected(4),
    Cancelling(5);

    private static final Map<Integer, Status> STATUS_MAP;

    static {
      STATUS_MAP = new HashMap<>();
      for (Status s : Status.values()) {
        STATUS_MAP.put(s.status, s);
      }
    }

    private final int status;

    Status(int status) {
      this.status = status;
    }

    @JsonCreator
    public static Status create(int status) {
      Status result = STATUS_MAP.get(status);
      if (result == null) {
        throw new RuntimeException(
            "Unknown transaction status: " + status + ", known are: " + STATUS_MAP.keySet());
      }
      return result;
    }
  }
}
