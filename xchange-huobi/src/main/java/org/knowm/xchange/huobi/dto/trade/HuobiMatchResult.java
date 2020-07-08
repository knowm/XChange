package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class HuobiMatchResult {

  private final long id;
  private final String symbol;
  private final String orderId;
  private final String matchId;
  private final String tradeId;
  private final BigDecimal price;
  private final Date createdAt;
  private final String type;
  private final BigDecimal filledAmount;
  private final BigDecimal filledFees;
  private final String feeCurrency;
  private final String source;
  private final String role;
  private final BigDecimal filledPoints;
  private final String feeDeductCurrency;

  public HuobiMatchResult(
      @JsonProperty("id") long id,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("order-id") String orderId,
      @JsonProperty("match-id") String matchId,
      @JsonProperty("trade-id") String tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("created-at") Date createdAt,
      @JsonProperty("type") String type,
      @JsonProperty("filled-amount") BigDecimal filledAmount,
      @JsonProperty("filled-fees") BigDecimal filledFees,
      @JsonProperty("fee-currency") String feeCurrency,
      @JsonProperty("source") String source,
      @JsonProperty("role") String role,
      @JsonProperty("filled-points") BigDecimal filledPoints,
      @JsonProperty("fee-deduct-currency") String feeDeductCurrency) {
    this.id = id;
    this.symbol = symbol;
    this.orderId = orderId;
    this.matchId = matchId;
    this.tradeId = tradeId;
    this.price = price;
    this.createdAt = createdAt;
    this.type = type;
    this.filledAmount = filledAmount;
    this.filledFees = filledFees;
    this.feeCurrency = feeCurrency;
    this.source = source;
    this.role = role;
    this.filledPoints = filledPoints;
    this.feeDeductCurrency = feeDeductCurrency;
  }

  public long getId() {
    return id;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getMatchId() {
    return matchId;
  }

  public String getTradeId() {
    return tradeId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getFilledAmount() {
    return filledAmount;
  }

  public BigDecimal getFilledFees() {
    return filledFees;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  public String getSource() {
    return source;
  }

  public String getRole() {
    return role;
  }

  public BigDecimal getFilledPoints() {
    return filledPoints;
  }

  public String getFeeDeductCurrency() {
    return feeDeductCurrency;
  }

  public boolean isLimit() { // startswith to support -fok and -ioc
    return getType().startsWith("buy-limit") || getType().startsWith("sell-limit");
  }

  public boolean isMarket() {
    return getType().equals("buy-market") || getType().equals("sell-market");
  }

  public boolean isStop() {
    return getType().startsWith("buy-stop") || getType().startsWith("sell-stop");
  }

  @Override
  public String toString() {
    return "HuobiMatchResult[" +
            "id=" + id +
            ", symbol='" + symbol + '\'' +
            ", orderId='" + orderId + '\'' +
            ", matchId='" + matchId + '\'' +
            ", tradeId='" + tradeId + '\'' +
            ", price=" + price +
            ", createdAt=" + createdAt +
            ", type='" + type + '\'' +
            ", filledAmount=" + filledAmount +
            ", filledFees=" + filledFees +
            ", feeCurrency='" + feeCurrency + '\'' +
            ", source='" + source + '\'' +
            ", role='" + role + '\'' +
            ", filledPoints='" + filledPoints + '\'' +
            ", feeDeductCurrency='" + feeDeductCurrency + '\'' +
            ']';
  }

}
