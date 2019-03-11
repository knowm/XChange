package org.knowm.xchange.quoine.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class QuoineTrade {
  public final String id;
  public final String currencyPairCode;
  public final String status;
  public final String side;
  public final BigDecimal marginUsed;
  public final BigDecimal openQuantity;
  public final BigDecimal closeQuantity;
  public final BigDecimal quantity;
  public final BigDecimal leverageLevel;
  public final String productCode;
  public final String productId;
  public final BigDecimal openPrice;
  public final BigDecimal closePrice;
  public final String traderId;
  public final BigDecimal openPnl;
  public final BigDecimal closePnl;
  public final BigDecimal pnl;
  public final BigDecimal stopLoss;
  public final BigDecimal takeProfit;
  public final String fundingCurrency;
  public final Long createdAt;
  public final Long updatedAt;
  public final BigDecimal totalInterest;

  public QuoineTrade(
      @JsonProperty("id") String id,
      @JsonProperty("currency_pair_code") String currencyPairCode,
      @JsonProperty("status") String status,
      @JsonProperty("side") String side,
      @JsonProperty("margin_used") BigDecimal marginUsed,
      @JsonProperty("open_quantity") BigDecimal openQuantity,
      @JsonProperty("close_quantity") BigDecimal closeQuantity,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("leverage_level") BigDecimal leverageLevel,
      @JsonProperty("product_code") String productCode,
      @JsonProperty("product_id") String productId,
      @JsonProperty("open_price") BigDecimal openPrice,
      @JsonProperty("close_price") BigDecimal closePrice,
      @JsonProperty("trader_id") String traderId,
      @JsonProperty("open_pnl") BigDecimal openPnl,
      @JsonProperty("close_pnl") BigDecimal closePnl,
      @JsonProperty("pnl") BigDecimal pnl,
      @JsonProperty("stop_loss") BigDecimal stopLoss,
      @JsonProperty("take_profit") BigDecimal takeProfit,
      @JsonProperty("funding_currency") String fundingCurrency,
      @JsonProperty("created_at") Long createdAt,
      @JsonProperty("updated_at") Long updatedAt,
      @JsonProperty("total_interest") BigDecimal totalInterest) {
    this.id = id;
    this.currencyPairCode = currencyPairCode;
    this.status = status;
    this.side = side;
    this.marginUsed = marginUsed;
    this.openQuantity = openQuantity;
    this.closeQuantity = closeQuantity;
    this.quantity = quantity;
    this.leverageLevel = leverageLevel;
    this.productCode = productCode;
    this.productId = productId;
    this.openPrice = openPrice;
    this.closePrice = closePrice;
    this.traderId = traderId;
    this.openPnl = openPnl;
    this.closePnl = closePnl;
    this.pnl = pnl;
    this.stopLoss = stopLoss;
    this.takeProfit = takeProfit;
    this.fundingCurrency = fundingCurrency;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.totalInterest = totalInterest;
  }

  @Override
  public String toString() {
    return "QuoineTrade{"
        + "id='"
        + id
        + '\''
        + ", currencyPairCode='"
        + currencyPairCode
        + '\''
        + ", status='"
        + status
        + '\''
        + ", side='"
        + side
        + '\''
        + ", marginUsed="
        + marginUsed
        + ", openQuantity="
        + openQuantity
        + ", closeQuantity="
        + closeQuantity
        + ", quantity="
        + quantity
        + ", leverageLevel="
        + leverageLevel
        + ", productCode='"
        + productCode
        + '\''
        + ", productId='"
        + productId
        + '\''
        + ", openPrice="
        + openPrice
        + ", closePrice="
        + closePrice
        + ", traderId='"
        + traderId
        + '\''
        + ", openPnl="
        + openPnl
        + ", closePnl="
        + closePnl
        + ", pnl="
        + pnl
        + ", stopLoss="
        + stopLoss
        + ", takeProfit="
        + takeProfit
        + ", fundingCurrency='"
        + fundingCurrency
        + '\''
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + ", totalInterest="
        + totalInterest
        + '}';
  }
}
