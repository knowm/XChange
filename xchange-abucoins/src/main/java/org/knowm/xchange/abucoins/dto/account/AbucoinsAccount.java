package org.knowm.xchange.abucoins.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbucoinsAccount {
  String id;
  String currency;
  BigDecimal balance;
  BigDecimal available;
  BigDecimal available_btc;
  BigDecimal hold;
  long profileID;
        
  public AbucoinsAccount(@JsonProperty("id") String id,
                         @JsonProperty("currency") String currency,
                         @JsonProperty("balance") BigDecimal balance,
                         @JsonProperty("available") BigDecimal available,
                         @JsonProperty("available_btc") BigDecimal available_btc,
                         @JsonProperty("hold") BigDecimal hold,
                         @JsonProperty("profile_id") long profileID) {
    this.id = id;
    this.currency = currency;
    this.balance = balance;
    this.available = available;
    this.available_btc = available_btc;
    this.hold = hold;
    this.profileID = profileID;
  }

  public String getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getAvailable_btc() {
    return available_btc;
  }

  public BigDecimal getHold() {
    return hold;
  }

  public long getProfileID() {
    return profileID;
  }

  @Override
  public String toString() {
    return "AbucoinsAccount [id=" + id + ", currency=" + currency + ", balance=" + balance + ", available="
        + available + ", available_btc=" + available_btc + ", hold=" + hold + ", profileID=" + profileID + "]";
  }
}
