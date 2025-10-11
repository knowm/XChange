package org.knowm.xchange.dase.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Immutable DTO for a single DASE account transaction (ledger entry). */
public class ApiAccountTxn {

  private final String id;
  private final String currency;
  private final String txnType;
  private final BigDecimal amount;
  private final long createdAt;
  private final String tradeId;
  private final String fundingId;

  @JsonCreator
  public ApiAccountTxn(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("txn_type") String txnType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("created_at") Number createdAt,
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("funding_id") String fundingId) {
    this.id = id;
    this.currency = currency;
    this.txnType = txnType;
    this.amount = amount;
    this.createdAt = createdAt == null ? 0L : createdAt.longValue();
    this.tradeId = tradeId;
    this.fundingId = fundingId;
  }

  public String getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public String getTxnType() {
    return txnType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public String getTradeId() {
    return tradeId;
  }

  public String getFundingId() {
    return fundingId;
  }
}
