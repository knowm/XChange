package org.knowm.xchange.okcoin.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinRecords {

  private final String address;

  private final String account;

  private final BigDecimal amount;

  private final String bank;

  private final String benificiaryAddress;

  private final BigDecimal transactionValue;

  private final BigDecimal fee;

  private final Long date;

  private final String status;

  public OkCoinRecords(@JsonProperty("addr") final String address, @JsonProperty("account") final String account,
                       @JsonProperty("amount") final BigDecimal amount, @JsonProperty("bank") final String bank,
                       @JsonProperty("benificiary_addr") final String benificiaryAddress, @JsonProperty("transaction_value") final BigDecimal transactionValue,
                       @JsonProperty("fee") final BigDecimal fee, @JsonProperty("date") final Long date, @JsonProperty("status") final String status) {

    this.address = address;
    this.account = account;
    this.amount = amount;
    this.bank = bank;
    this.benificiaryAddress = benificiaryAddress;
    this.transactionValue = transactionValue;
    this.fee = fee;
    this.date = date;
    this.status = status;
  }

  public String getAddress() {

    return address;
  }

  public String getAccount() {

    return account;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getBank() {

    return bank;
  }

  public String getBenificiaryAddress() {

    return benificiaryAddress;
  }

  public BigDecimal getTransactionValue() {

    return transactionValue;
  }

  public BigDecimal getFee() {

    return fee;
  }
  public Long getDate() {

    return date;
  }

  public String getStatus() {
    return status;
  }
}
