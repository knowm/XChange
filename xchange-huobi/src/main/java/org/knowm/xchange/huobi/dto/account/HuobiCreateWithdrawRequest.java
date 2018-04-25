package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiCreateWithdrawRequest {

  @JsonProperty("address")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String address;

  @JsonProperty("amount")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String amount;

  @JsonProperty("currency")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String currency;

  @JsonProperty("fee")
  private String fee;

  @JsonProperty("addr-tag")
  private String addrTag;

  public HuobiCreateWithdrawRequest(
      String address, String amount, String currency, String fee, String addrTag) {
    this.address = address;
    this.amount = amount;
    this.currency = currency;
    this.fee = fee;
    this.addrTag = addrTag;
  }

  public String getAddress() {
    return address;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getFee() {
    return fee;
  }

  public String getAddressTag() {
    return addrTag;
  }
}
