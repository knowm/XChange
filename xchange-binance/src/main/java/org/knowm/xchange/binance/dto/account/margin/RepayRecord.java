package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class RepayRecord {

  private BigDecimal amount;
  private Currency asset;
  private BigDecimal interest;
  private BigDecimal principal;
  private LoanStatus status;
  private Long timestamp;
  private Long txId;

  public RepayRecord(
          @JsonProperty("amount") BigDecimal amount,
          @JsonProperty("asset") String asset,
          @JsonProperty("interest") BigDecimal interest,
          @JsonProperty("principal") BigDecimal principal,
          @JsonProperty("status") String status,
          @JsonProperty("timestamp") Long timestamp,
          @JsonProperty("txId") Long txId) {
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
