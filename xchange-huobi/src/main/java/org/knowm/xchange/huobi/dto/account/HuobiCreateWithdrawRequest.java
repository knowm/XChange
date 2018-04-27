package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class HuobiCreateWithdrawRequest {

  @JsonProperty("address")
  private String address;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("fee")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private BigDecimal fee;

  @JsonProperty("addr-tag")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String addressTag;

  public HuobiCreateWithdrawRequest(
      String address, BigDecimal amount, String currency, BigDecimal fee, String addressTag) {
    this.address = address;
    this.amount = amount;
    this.currency = currency;
    this.fee = fee;
    this.addressTag = addressTag;
  }

  public String getAddress() {
    return address;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getAddressTag() {
    return addressTag;
  }
}
