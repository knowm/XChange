package org.knowm.xchange.dto.account;

import java.math.BigDecimal;

public final class FundsRecord {
  private final String address;
  private final Long date;
  private final String ccy;
  private final BigDecimal amount;
  private final String ref;
  private final String fundsType;
  private final String status;
  private final BigDecimal balance;
  private final BigDecimal fee;

  public FundsRecord(final String address, final Long date, final String ccy, final BigDecimal amount, final String ref,
                     final String fundsType, final String status, final BigDecimal balance, final BigDecimal fee){
    this.address = address;
    this.date = date;
    this.ccy = ccy;
    this.amount = amount;
    this.ref = ref;
    this.fundsType = fundsType;
    this.status = status;
    this.balance = balance;
    this.fee = fee;
  }

  public String getAddress() {
    return address;
  }

  public Long getDate() {
    return date;
  }

  public String getCcy() {
    return ccy;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getRef() {
    return ref;
  }

  public String getFundsType() {
    return fundsType;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getFee() {
    return fee;
  }

  @Override
  public String toString() {
    return "FundsRecord{" +
            "address='" + address + '\'' +
            ", date=" + date +
            ", ccy='" + ccy + '\'' +
            ", amount=" + amount +
            ", ref='" + ref + '\'' +
            ", fundsType='" + fundsType + '\'' +
            ", status='" + status + '\'' +
            ", balance=" + balance +
            ", fee=" + fee +
            '}';
  }
}
