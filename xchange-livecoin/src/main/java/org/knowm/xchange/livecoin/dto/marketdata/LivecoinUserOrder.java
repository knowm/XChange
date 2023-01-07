package org.knowm.xchange.livecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class LivecoinUserOrder {
  private final Long id;
  private final String currencyPair;
  private final Long goodUntilTime;
  private final String type;
  private final String orderStatus;
  private final Long issueTime;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal remainingQuantity;
  private final BigDecimal commission;
  private final BigDecimal commissionRate;
  private final Long lastModificationTime;

  public LivecoinUserOrder(
      @JsonProperty("id") Long id,
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("goodUntilTime") Long goodUntilTime,
      @JsonProperty("type") String type,
      @JsonProperty("orderStatus") String orderStatus,
      @JsonProperty("issueTime") Long issueTime,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("remainingQuantity") BigDecimal remainingQuantity,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("commissionRate") BigDecimal commissionRate,
      @JsonProperty("lastModificationTime") Long lastModificationTime) {
    this.id = id;
    this.currencyPair = currencyPair;
    this.goodUntilTime = goodUntilTime;
    this.type = type;
    this.orderStatus = orderStatus;
    this.issueTime = issueTime;
    this.price = price;
    this.quantity = quantity;
    this.remainingQuantity = remainingQuantity;
    this.commission = commission;
    this.commissionRate = commissionRate;
    this.lastModificationTime = lastModificationTime;
  }

  public Long getId() {
    return id;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public Long getGoodUntilTime() {
    return goodUntilTime;
  }

  public String getType() {
    return type;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public Long getIssueTime() {
    return issueTime;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getRemainingQuantity() {
    return remainingQuantity;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public BigDecimal getCommissionRate() {
    return commissionRate;
  }

  public Long getLastModificationTime() {
    return lastModificationTime;
  }
}
