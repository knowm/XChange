package org.knowm.xchange.exx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EXXOrder {

  @JsonProperty("fees")
  private BigDecimal fees;

  @JsonProperty("total_amount")
  private BigDecimal totalAmount;

  @JsonProperty("trade_amount")
  private BigDecimal tradeAmount;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("id")
  private String id;

  @JsonProperty("trade_money")
  private BigDecimal tradeMoney;

  @JsonProperty("type")
  private String type;

  @JsonProperty("trade_date")
  private Long tradeDate;

  @JsonProperty("status")
  private int status;

  /** No args constructor for use in serialization */
  public EXXOrder() {}

  /**
   * @param id
   * @param tradeDate
   * @param tradeMoney
   * @param price
   * @param status
   * @param totalAmount
   * @param fees
   * @param tradeAmount
   * @param type
   * @param currency
   */
  public EXXOrder(
      BigDecimal fees,
      BigDecimal totalAmount,
      BigDecimal tradeAmount,
      BigDecimal price,
      String currency,
      String id,
      BigDecimal tradeMoney,
      String type,
      Long tradeDate,
      int status) {
    super();
    this.fees = fees;
    this.totalAmount = totalAmount;
    this.tradeAmount = tradeAmount;
    this.price = price;
    this.currency = currency;
    this.id = id;
    this.tradeMoney = tradeMoney;
    this.type = type;
    this.tradeDate = tradeDate;
    this.status = status;
  }

  @JsonProperty("fees")
  public BigDecimal getFees() {
    return fees;
  }

  @JsonProperty("fees")
  public void setFees(BigDecimal fees) {
    this.fees = fees;
  }

  @JsonProperty("total_amount")
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  @JsonProperty("total_amount")
  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  @JsonProperty("trade_amount")
  public BigDecimal getTradeAmount() {
    return tradeAmount;
  }

  @JsonProperty("trade_amount")
  public void setTradeAmount(BigDecimal tradeAmount) {
    this.tradeAmount = tradeAmount;
  }

  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonProperty("trade_money")
  public BigDecimal getTradeMoney() {
    return tradeMoney;
  }

  @JsonProperty("trade_money")
  public void setTradeMoney(BigDecimal tradeMoney) {
    this.tradeMoney = tradeMoney;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("trade_date")
  public Long getTradeDate() {
    return tradeDate;
  }

  @JsonProperty("trade_date")
  public void setTradeDate(Long tradeDate) {
    this.tradeDate = tradeDate;
  }

  @JsonProperty("status")
  public int getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("fees", fees)
        .append("totalAmount", totalAmount)
        .append("tradeAmount", tradeAmount)
        .append("price", price)
        .append("currency", currency)
        .append("id", id)
        .append("tradeMoney", tradeMoney)
        .append("type", type)
        .append("tradeDate", tradeDate)
        .append("status", status)
        .toString();
  }
}
