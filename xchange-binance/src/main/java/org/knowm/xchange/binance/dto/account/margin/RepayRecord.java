package org.knowm.xchange.binance.dto.account.margin;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

public class RepayRecord {

  private BigDecimal amount;
  private Currency asset;
  private BigDecimal interest;
  private BigDecimal principal;
  private LoanStatus status;
  private Long timestamp;
  private Long txId;

  public RepayRecord(
      BigDecimal amount,
      String asset,
      BigDecimal interest,
      BigDecimal principal,
      String status,
      Long timestamp,
      Long txId) {
    this.amount = amount;
    this.asset = Currency.getInstance(asset);
    this.interest = interest;
    this.principal = principal;
    this.status = LoanStatus.valueOf(status);
    this.timestamp = timestamp;
    this.txId = txId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Currency getAsset() {
    return asset;
  }

  public void setAsset(Currency asset) {
    this.asset = asset;
  }

  public BigDecimal getInterest() {
    return interest;
  }

  public void setInterest(BigDecimal interest) {
    this.interest = interest;
  }

  public BigDecimal getPrincipal() {
    return principal;
  }

  public void setPrincipal(BigDecimal principal) {
    this.principal = principal;
  }

  public LoanStatus getStatus() {
    return status;
  }

  public void setStatus(LoanStatus status) {
    this.status = status;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Long getTxId() {
    return txId;
  }

  public void setTxId(Long txId) {
    this.txId = txId;
  }
}
