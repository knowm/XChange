package org.knowm.xchange.gatecoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author sumedha */
public class GatecoinTradeHistory {
  private final long transactionId;
  private final String transactionTime;
  private final String askOrderID;
  private final String bidOrderID;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final String currencyPair;
  private final String way;
  private final BigDecimal feeRate;

  public GatecoinTradeHistory(
      @JsonProperty("transactionId") long transactionId,
      @JsonProperty("transactionTime") String transactionTime,
      @JsonProperty("askOrderID") String askOrderID,
      @JsonProperty("bidOrderID") String bidOrderID,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("way") String way,
      @JsonProperty("feeRate") BigDecimal feeRate) {
    this.transactionId = transactionId;
    this.transactionTime = transactionTime;
    this.askOrderID = askOrderID;
    this.bidOrderID = bidOrderID;
    this.price = price;
    this.quantity = quantity;
    this.currencyPair = currencyPair;
    this.way = way;
    this.feeRate = feeRate;
  }

  public long getTransactionId() {
    return transactionId;
  }

  public String getTransactionTime() {
    return transactionTime;
  }

  public String getAskOrderID() {
    return askOrderID;
  }

  public String getBidOrderID() {
    return bidOrderID;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public String getWay() {
    return way;
  }

  public BigDecimal getFeeRate() {
    return feeRate;
  }

  @Override
  public String toString() {
    return "Transaction Id= "
        + transactionId
        + ",transaction Time= "
        + transactionTime
        + ",askOrderID= "
        + askOrderID
        + ",bidOrderID= "
        + bidOrderID
        + ",price= "
        + price
        + ",quantity= "
        + quantity
        + ",currencyPair= "
        + currencyPair
        + ",way= "
        + way
        + ",feeRate= "
        + feeRate;
  }
}
