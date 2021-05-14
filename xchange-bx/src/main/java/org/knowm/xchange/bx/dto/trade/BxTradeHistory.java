package org.knowm.xchange.bx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxTradeHistory {

  private final long transactionId;
  private final String currency;
  private final BigDecimal amount;
  private final String date;
  private final String type;
  private final long refId;

  public BxTradeHistory(
      @JsonProperty("transaction_id") long transactionId,
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("date") String date,
      @JsonProperty("type") String type,
      @JsonProperty("ref_id") long refId) {
    this.transactionId = transactionId;
    this.currency = currency;
    this.amount = amount;
    this.date = date;
    this.type = type;
    this.refId = refId;
  }

  public long getTransactionId() {
    return transactionId;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getDate() {
    return date;
  }

  public String getType() {
    return type;
  }

  public long getRefId() {
    return refId;
  }

  @Override
  public String toString() {
    return "BxTradeHistory{"
        + "transactionId="
        + transactionId
        + ", currency='"
        + currency
        + '\''
        + ", amount="
        + amount
        + ", date="
        + date
        + ", type='"
        + type
        + '\''
        + ", refId="
        + refId
        + '}';
  }
}
