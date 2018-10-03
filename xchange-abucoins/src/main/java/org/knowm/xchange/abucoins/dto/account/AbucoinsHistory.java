package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Base class for the the history related API calls (withdrawals and deposits)
 *
 * @author bryant_harris
 */
public abstract class AbucoinsHistory {
  /** Deposit currency */
  String currency;

  /** Date of deposit */
  String date;

  /** Deposit amount */
  BigDecimal amount;

  /** Deposit fee */
  BigDecimal fee;

  // Not directly storing status as a Status enum, doing so introduces
  // risk of a parsing error due to a new, unrecognized status.
  /** Deposit status */
  String status;

  /** blockchain explorer url (null if not available) */
  String url;

  /** Error codes */
  String message;

  public AbucoinsHistory(
      @JsonProperty("currency") String currency,
      @JsonProperty("date") String date,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("status") String status,
      @JsonProperty("url") String url,
      @JsonProperty("message") String message) {
    this.currency = currency;
    this.date = date;
    this.amount = amount;
    this.fee = fee;
    this.status = status;
    this.url = url;
    this.message = message;
  }

  public String getCurrency() {
    return currency;
  }

  public String getDate() {
    return date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public Status getStatus() {
    return Status.fromString(status);
  }

  /**
   * Returns the raw string value of the status, useful if it's a newer status type
   *
   * @return
   */
  public String getStatusRaw() {
    return status;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public enum Status {
    /** Deposit wait for email confirmation */
    awaitingEmailConfirmation,

    /** Deposit is pending */
    pending,

    /** Deposit is completed (documentation lists complete, api seems to return completed */
    complete,
    completed,

    /** Deposit was sent */
    sent,

    unknown; // we can't parse it

    public static Status fromString(String s) {
      try {
        return Status.valueOf(s);
      } catch (Exception e) {
        if (s.equals("awaiting-email-onfirmation")) return awaitingEmailConfirmation;
      }

      return unknown;
    }
  }
}
