package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeOrder {

  private final long id;
  private final long orderDate;
  private final CurrencyPair currencyPair;
  private final CryptoTradeOrderType type;
  private final BigDecimal amount;
  private final BigDecimal initialAmount;
  private final BigDecimal remainingAmount;
  private final BigDecimal rate;
  private final String status;

  private CryptoTradeOrder(@JsonProperty("id") long id,
      @JsonProperty("pair") @JsonDeserialize(using = CurrencyPairDeserializer.class) CurrencyPair currencyPair,
      @JsonProperty("type") CryptoTradeOrderType type, @JsonProperty("rate") BigDecimal rate, @JsonProperty("status") String status,
      @JsonProperty("orderdate") long orderDate, @JsonProperty("amount") BigDecimal amount, @JsonProperty("initial_amount") BigDecimal initialAmount,
      @JsonProperty("remaining_amount") BigDecimal remainingAmount) {

    this.id = id; // OrderInfo & OrderHistory
    this.orderDate = orderDate; // OrderInfo
    this.currencyPair = currencyPair; // OrderInfo & OrderHistory
    this.type = type; // OrderInfo & OrderHistory
    this.amount = amount; // OrderHistory
    this.initialAmount = initialAmount; // OrderInfo
    this.remainingAmount = remainingAmount; // OrderInfo
    this.rate = rate; // OrderInfo & OrderHistory
    this.status = status; // OrderInfo & OrderHistory
  }

  public long getId() {

    return id;
  }

  public long getOrderDate() {

    return orderDate;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  public CryptoTradeOrderType getType() {

    return type;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getInitialAmount() {

    return initialAmount;
  }

  public BigDecimal getRemainingAmount() {

    return remainingAmount;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public String getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return "CryptoTradeOrder [id=" + getId() + ", orderdate=" + getOrderDate() + ", currencyPair=" + getCurrencyPair() + ", type=" + getType()
        + ", amount=" + getAmount() + ", initialAmount=" + getInitialAmount() + ", remainingAmount=" + getRemainingAmount() + ", rate=" + getRate()
        + ", status=" + getStatus() + "]";
  }
}