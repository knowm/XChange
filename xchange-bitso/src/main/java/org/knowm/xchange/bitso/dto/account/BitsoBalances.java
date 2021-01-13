package org.knowm.xchange.bitso.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitsoBalances {

  private String currency;
  private String available;
  private String locked;
  private String total;
  private String pendingDeposit;
  private String pendingWithdrawal;

  public BitsoBalances(
      @JsonProperty("currency") String currency,
      @JsonProperty("available") String available,
      @JsonProperty("locked") String locked,
      @JsonProperty("total") String total,
      @JsonProperty("pending_deposit") String pendingDeposit,
      @JsonProperty("pending_withdrawal") String pendingWithdrawal) {

    this.currency = currency;
    this.available = available;
    this.locked = locked;
    this.total = total;
    this.pendingDeposit = pendingDeposit;
    this.pendingWithdrawal = pendingWithdrawal;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getAvailable() {
    return available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  public String getLocked() {
    return locked;
  }

  public void setLocked(String locked) {
    this.locked = locked;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getPendingDeposit() {
    return pendingDeposit;
  }

  public void setPendingDeposit(String pendingDeposit) {
    this.pendingDeposit = pendingDeposit;
  }

  public String getPendingWithdrawal() {
    return pendingWithdrawal;
  }

  public void setPendingWithdrawal(String pendingWithdrawal) {
    this.pendingWithdrawal = pendingWithdrawal;
  }

  @Override
  public String toString() {
    return "BitsoBalances [currency="
        + currency
        + ", available="
        + available
        + ", locked="
        + locked
        + ", total="
        + total
        + ", pendingDeposit="
        + pendingDeposit
        + ", pendingWithdrawal="
        + pendingWithdrawal
        + "]";
  }
}
