package org.knowm.xchange.quoine.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public final class QuoineTradingAccountInfo {
  private final String id;
  private final int leverageLevel;
  private final int currentLeverageLevel;
  private final BigDecimal equity;
  private final BigDecimal margin;
  private final BigDecimal freeMargin;
  private final long traderId;
  private final String status;
  private final String productCode;
  private final String currencyPairCode;
  private final BigDecimal pnl;
  private final BigDecimal position;
  private final BigDecimal balance;
  private final Date updatedAt;
  private final String pusher_channel;
  private final BigDecimal marginPercent;
  private final String fundingCurrency;

  public QuoineTradingAccountInfo(
      @JsonProperty("id") String id,
      @JsonProperty("leverage_level") int leverageLevel,
      @JsonProperty("current_leverage_level") int currentLeverageLevel,
      @JsonProperty("equity") BigDecimal equity,
      @JsonProperty("margin") BigDecimal margin,
      @JsonProperty("free_margin") BigDecimal freeMargin,
      @JsonProperty("trader_id") long traderId,
      @JsonProperty("status") String status,
      @JsonProperty("product_code") String productCode,
      @JsonProperty("currency_pair_code") String currencyPairCode,
      @JsonProperty("pnl") BigDecimal pnl,
      @JsonProperty("position") BigDecimal position,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("updated_at") Date updatedAt,
      @JsonProperty("pusher_channel") String pusher_channel,
      @JsonProperty("margin_percent") BigDecimal marginPercent,
      @JsonProperty("funding_currency") String fundingCurrency) {
    super();
    this.id = id;
    this.leverageLevel = leverageLevel;
    this.currentLeverageLevel = currentLeverageLevel;
    this.equity = equity;
    this.margin = margin;
    this.freeMargin = freeMargin;
    this.traderId = traderId;
    this.status = status;
    this.productCode = productCode;
    this.currencyPairCode = currencyPairCode;
    this.pnl = pnl;
    this.position = position;
    this.balance = balance;
    this.updatedAt = updatedAt;
    this.pusher_channel = pusher_channel;
    this.marginPercent = marginPercent;
    this.fundingCurrency = fundingCurrency;
  }

  public String getId() {
    return id;
  }

  public int getLeverageLevel() {
    return leverageLevel;
  }

  public int getCurrentLeverageLevel() {
    return currentLeverageLevel;
  }

  public BigDecimal getEquity() {
    return equity;
  }

  public BigDecimal getMargin() {
    return margin;
  }

  public BigDecimal getFreeMargin() {
    return freeMargin;
  }

  public long getTraderId() {
    return traderId;
  }

  public String getStatus() {
    return status;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getCurrencyPairCode() {
    return currencyPairCode;
  }

  public BigDecimal getPnl() {
    return pnl;
  }

  public BigDecimal getPosition() {
    return position;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public String getPusher_channel() {
    return pusher_channel;
  }

  public BigDecimal getMarginPercent() {
    return marginPercent;
  }

  public String getFundingCurrency() {
    return fundingCurrency;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuoineTradingAccountInfo [id=");
    builder.append(id);
    builder.append(", leverageLevel=");
    builder.append(leverageLevel);
    builder.append(", equity=");
    builder.append(equity);
    builder.append(", margin=");
    builder.append(margin);
    builder.append(", currencyPairCode=");
    builder.append(currencyPairCode);
    builder.append(", pnl=");
    builder.append(pnl);
    builder.append(", position=");
    builder.append(position);
    builder.append(", balance=");
    builder.append(balance);
    builder.append(", marginPercent=");
    builder.append(marginPercent);
    builder.append("]");
    return builder.toString();
  }
}
