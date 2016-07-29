package org.knowm.xchange.bitstamp.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositTransaction {

  public DepositTransaction(@JsonProperty("amount") BigDecimal amount, @JsonProperty("confirmations") Integer confirmations,
      @JsonProperty("address") String address) {
    this.amount = amount;
    this.confirmations = confirmations;
    this.address = address;
  }

  private BigDecimal amount;

  private Integer confirmations;

  private String address;

  public BigDecimal getAmount() {
    return amount;
  }

  public Integer getConfirmations() {
    return confirmations;
  }

  public String getAddress() {
    return address;
  }

  @Override
  public String toString() {
    return String.format("DepositTransaction{amount=%s, confirmations=%d, address='%s'}", amount, confirmations, address);
  }
}
