package org.knowm.xchange.bankera.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankeraWallet {
  private int id;
  private String balance;
  private String reserved;
  private String total;
  private String currency;

  public BankeraWallet(
      @JsonProperty("id") int id,
      @JsonProperty("balance") String balance,
      @JsonProperty("reserved") String reserved,
      @JsonProperty("total") String total,
      @JsonProperty("currency") String currency) {
    this.id = id;
    this.balance = balance;
    this.reserved = reserved;
    this.total = total;
    this.currency = currency;
  }

  public int getId() {
    return id;
  }

  public String getBalance() {
    return balance;
  }

  public String getReserved() {
    return reserved;
  }

  public String getTotal() {
    return total;
  }

  public String getCurrency() {
    return currency;
  }
}
