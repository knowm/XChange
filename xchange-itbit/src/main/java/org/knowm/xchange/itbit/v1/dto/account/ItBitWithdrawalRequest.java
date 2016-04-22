package org.knowm.xchange.itbit.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitWithdrawalRequest {

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("amount")
  protected String amount;

  @JsonProperty("address")
  protected String address;

  public ItBitWithdrawalRequest(String currency, String amount, String address) {

    super();
    this.currency = currency;
    this.amount = amount;
    this.address = address;
  }

  public String getCurrency() {

    return currency;
  }

  public String getAmount() {

    return amount;
  }

  public String getAddress() {

    return address;
  }
}
