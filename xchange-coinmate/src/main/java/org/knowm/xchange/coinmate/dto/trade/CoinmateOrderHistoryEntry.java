package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateOrderHistoryEntry {

  private final long id;
  private final long timestamp;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal remainingAmount;
  private final BigDecimal originalAmount;
  private final String status;

  public CoinmateOrderHistoryEntry(
      @JsonProperty("id") long id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("type") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("remainingAmount") BigDecimal remainingAmount,
      @JsonProperty("originalAmount") BigDecimal originalAmount,
      @JsonProperty("status") String status) {

    this.id = id;
    this.timestamp = timestamp;
    this.type = type;
    this.price = price;
    this.remainingAmount = remainingAmount;
    this.originalAmount = originalAmount;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getRemainingAmount() {
    return remainingAmount;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public String getStatus() {
    return status;
  }
}
