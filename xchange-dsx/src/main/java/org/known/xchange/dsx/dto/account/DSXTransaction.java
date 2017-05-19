package org.known.xchange.dsx.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTransaction {

  private final long id;
  private final long timestamp;
  private final Type type;
  private final BigDecimal amount;
  private final String currency;
  private final String address;
  private final Status status;

  public DSXTransaction(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp, @JsonProperty("type") Type type,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency, @JsonProperty("address") String address, @JsonProperty
      ("status") Status status) {

    this.id = id;
    this.timestamp = timestamp;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.address = address;
    this.status = status;
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

  public String getAddress() {
    return address;
  }

  public Status getStatus() {
    return status;
  }

  public enum Type {
    Withdraw, Incoming
  }

  public enum Status {
    reserved0, Failed, Completed, Processing, Rejected
  }
}
