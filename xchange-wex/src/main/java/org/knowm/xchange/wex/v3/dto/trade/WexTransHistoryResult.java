package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.MessageFormat;

/** @author Peter N. Steinmetz Date: 3/30/15 Time: 3:19 PM */
public class WexTransHistoryResult {

  private final Type type; // Transaction type. 1/2 - deposit/withdrawal, 4/5 - credit/debit
  private final BigDecimal amount;
  private final String currency;
  private final String description;
  private final Status
      status; // 0 - canceled/failed, 1 - waiting for acceptance, 2 - successful, 3 – not confirmed
  private final Long timestamp;

  /**
   * Constructor
   *
   * @param type
   * @param amount
   * @param currency
   * @param description
   * @param status
   * @param timestamp
   */
  public WexTransHistoryResult(
      @JsonProperty("type") Type type,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("desc") String description,
      @JsonProperty("status") Status status,
      @JsonProperty("timestamp") Long timestamp) {

    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.description = description;
    this.status = status;
    this.timestamp = timestamp;
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

  public String getDescription() {
    return description;
  }

  public Status getStatus() {
    return status;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return MessageFormat.format(
        "BTCETransHistory[type={0}, amount={1}, currency=''{2}'', description=''{3}'', status={4}, timestamp={5}]",
        type, amount, currency, description, status, timestamp);
  }

  /** Type of transaction. */
  public enum Type {
    reserved0,
    BTC_deposit,
    BTC_withdrawal,
    reserved3,
    credit,
    payment,
    reserved6,
    reserved7,
    reserved8
  }

  /** Status of transaction. */
  public enum Status {
    entered,
    waiting,
    complete
  }
}
