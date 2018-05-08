package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class HuobiOrder {

  private final long accountID;
  private final BigDecimal amount;
  private final Date canceledAt;
  private final Date createdAt;
  private final BigDecimal fieldAmount;
  private final BigDecimal fieldCashAmount;
  private final BigDecimal fieldFees;
  private final Date finishedAt;
  private final long id;
  private final BigDecimal price;
  private final String source;
  private final String state;
  private final String symbol;
  private final String type;

  public HuobiOrder(
      @JsonProperty("account-id") long accountID,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("canceled-at") Date canceledAt,
      @JsonProperty("created-at") Date createdAt,
      @JsonProperty("field-amount") BigDecimal fieldAmount,
      @JsonProperty("field-cash-amount") BigDecimal fieldCashAmount,
      @JsonProperty("field-fees") BigDecimal fieldFees,
      @JsonProperty("finished-at") Date finishedAt,
      @JsonProperty("id") long id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("source") String source,
      @JsonProperty("state") String state,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("type") String type) {
    this.accountID = accountID;
    this.amount = amount;
    this.canceledAt = canceledAt;
    this.createdAt = createdAt;
    this.fieldAmount = fieldAmount;
    this.fieldCashAmount = fieldCashAmount;
    this.fieldFees = fieldFees;
    this.finishedAt = finishedAt;
    this.id = id;
    this.price = price;
    this.source = source;
    this.state = state;
    this.symbol = symbol;
    this.type = type;
  }

  public long getAccountID() {
    return accountID;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getCanceledAt() {
    return canceledAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getFieldAmount() {
    return fieldAmount;
  }

  public BigDecimal getFieldCashAmount() {
    return fieldCashAmount;
  }

  public BigDecimal getFieldFees() {
    return fieldFees;
  }

  public Date getFinishedAt() {
    return finishedAt;
  }

  public long getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getSource() {
    return source;
  }

  public String getState() {
    return state;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getType() {
    return type;
  }

  public boolean isLimit() {
    return getType().equals("buy-limit") || getType().equals("sell-limit");
  }

  public boolean isMarket() {
    return getType().equals("buy-market") || getType().equals("sell-market");
  }
}
