package org.knowm.xchange.exx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EXXBalance {

  @JsonProperty("total")
  private BigDecimal total;

  @JsonProperty("freeze")
  private BigDecimal freeze;

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("fundsType")
  private String fundsType;

  @JsonProperty("propTag")
  private String propTag;

  @JsonProperty("credit_quota")
  private BigDecimal creditQuota;

  @JsonProperty("credit_borrowed")
  private BigDecimal creditBorrowed;

  @JsonProperty("credit_interest")
  private BigDecimal creditInterest;

  /** No args constructor for use in serialization */
  public EXXBalance() {}

  /**
   * @param creditBorrowed
   * @param total
   * @param balance
   * @param creditQuota
   * @param freeze
   * @param creditInterest
   * @param propTag
   * @param fundsType
   */
  public EXXBalance(
      BigDecimal total,
      BigDecimal freeze,
      BigDecimal balance,
      String fundsType,
      String propTag,
      BigDecimal creditQuota,
      BigDecimal creditBorrowed,
      BigDecimal creditInterest) {
    super();
    this.total = total;
    this.freeze = freeze;
    this.balance = balance;
    this.fundsType = fundsType;
    this.propTag = propTag;
    this.creditQuota = creditQuota;
    this.creditBorrowed = creditBorrowed;
    this.creditInterest = creditInterest;
  }

  @JsonProperty("total")
  public BigDecimal getTotal() {
    return total;
  }

  @JsonProperty("total")
  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  @JsonProperty("freeze")
  public BigDecimal getFreeze() {
    return freeze;
  }

  @JsonProperty("freeze")
  public void setFreeze(BigDecimal freeze) {
    this.freeze = freeze;
  }

  @JsonProperty("balance")
  public BigDecimal getBalance() {
    return balance;
  }

  @JsonProperty("balance")
  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @JsonProperty("fundsType")
  public String getFundsType() {
    return fundsType;
  }

  @JsonProperty("fundsType")
  public void setFundsType(String fundsType) {
    this.fundsType = fundsType;
  }

  @JsonProperty("propTag")
  public String gEXXBalanceropTag() {
    return propTag;
  }

  @JsonProperty("propTag")
  public void sEXXBalanceropTag(String propTag) {
    this.propTag = propTag;
  }

  @JsonProperty("credit_quota")
  public BigDecimal getCreditQuota() {
    return creditQuota;
  }

  @JsonProperty("credit_quota")
  public void setCreditQuota(BigDecimal creditQuota) {
    this.creditQuota = creditQuota;
  }

  @JsonProperty("credit_borrowed")
  public BigDecimal getCreditBorrowed() {
    return creditBorrowed;
  }

  @JsonProperty("credit_borrowed")
  public void setCreditBorrowed(BigDecimal creditBorrowed) {
    this.creditBorrowed = creditBorrowed;
  }

  @JsonProperty("credit_interest")
  public BigDecimal getCreditInterest() {
    return creditInterest;
  }

  @JsonProperty("credit_interest")
  public void setCreditInterest(BigDecimal creditInterest) {
    this.creditInterest = creditInterest;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("total", total)
        .append("freeze", freeze)
        .append("balance", balance)
        .append("fundsType", fundsType)
        .append("propTag", propTag)
        .append("creditQuota", creditQuota)
        .append("creditBorrowed", creditBorrowed)
        .append("creditInterest", creditInterest)
        .toString();
  }
}
