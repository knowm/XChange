package com.xeiam.xchange.btcchina.dto.account;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joe Zhou
 */
public class BTCChinaWithdrawal {

  private final long id;
  private final String address;
  private final String currency;
  private final BigDecimal amount;
  private final long date;
  private final String transaction;
  private final String status;

  public BTCChinaWithdrawal(@JsonProperty("id") long id, @JsonProperty("address") String address, @JsonProperty("currency") String currency, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("date") long date, @JsonProperty("transaction") String transaction, @JsonProperty("status") String status) {

    this.id = id;
    this.address = address;
    this.currency = currency;
    this.amount = amount;
    this.date = date;
    this.transaction = transaction;
    this.status = status;
  }

  public long getId() {

    return id;
  }

  public String getAddress() {

    return address;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public String getTransaction() {

    return transaction;
  }

  public String getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
