package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class LoanRecord {

  private Currency asset;
  private BigDecimal principal;
  private Long timestamp;
  private LoanStatus status;

  public LoanRecord(
          @JsonProperty("asset") String asset,
          @JsonProperty("principal") BigDecimal principal,
          @JsonProperty("timestamp") Long timestamp,
          @JsonProperty("status") String status) {
    this.asset = Currency.getInstance(asset);
    this.principal = principal;
    this.timestamp = timestamp;
    this.status = LoanStatus.valueOf(status);
  }

  public Currency getAsset() {
    return asset;
  }

  public void setAsset(Currency asset) {
    this.asset = asset;
  }

  public BigDecimal getPrincipal() {
    return principal;
  }

  public void setPrincipal(BigDecimal principal) {
    this.principal = principal;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public LoanStatus getStatus() {
    return status;
  }

  public void setStatus(LoanStatus status) {
    this.status = status;
  }
}
