package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTransHistoryResult {

  private final long id;
  private final Type type;
  private final BigDecimal amount;
  private final String currency;
  private final String desc;
  private final Status status;
  private final Long timestamp;
  private final BigDecimal commission;
  private final String address;
  private final String txId;

  public DSXTransHistoryResult(@JsonProperty("id") long id, @JsonProperty("type") Type type, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency, @JsonProperty("desc") String desc, @JsonProperty("status") Status status,
      @JsonProperty("timestamp") Long timestamp, @JsonProperty("commission") BigDecimal commission, @JsonProperty("address") String address,
      @JsonProperty("txid") String txId) {

    this.id = id;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.desc = desc;
    this.status = status;
    this.timestamp = timestamp;
    this.commission = commission;
    this.address = address;
    this.txId = txId;
  }

  public long getId() {
    return id;
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

  public String getDesc() {
    return desc;
  }

  public Status getStatus() {
    return status;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public String getAddress() {
    return address;
  }

  public String getTxId() {

    return txId;
  }

  @Override
  public String toString() {
    return MessageFormat.format("DSXTransHistory[id={0}, type={1}, amount={2}, currency=''{3}'', description=''{4}'', status={5}, timestamp={6}, " +
        "commission={7}, address=''{8}'', txId={9}]", id, type, amount, currency, desc, status, timestamp, commission, address, txId);
  }
  /**
   * Type of transaction
   */
  public enum Type {
      Incoming, Withdraw
  }

  /**
   * Status of transaction
   */
  public enum Status {
    reserved0, Failed, Completed, Processing, Rejected
  }
}
