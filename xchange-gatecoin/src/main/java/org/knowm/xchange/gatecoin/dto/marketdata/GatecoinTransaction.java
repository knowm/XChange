package org.knowm.xchange.gatecoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sumedha
 */
public class GatecoinTransaction {

  private final long transactionTime;
  private final int transactionId;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final String currencyPair;

  /**
   * Constructor
   *
   * @param transactionTime
   * @param transactionId
   * @param price BTC price
   * @param quantity
   * @param currencyPair
   */
  public GatecoinTransaction(@JsonProperty("transactionTime") long transactionTime, @JsonProperty("transactionId") int transactionId,
      @JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("currencyPair") String currencyPair) {

    this.transactionTime = transactionTime;
    this.transactionId = transactionId;
    this.price = price;
    this.quantity = quantity;
    this.currencyPair = currencyPair;
  }

  public long getTransacationTime() {
    return transactionTime;
  }

  public int getTransactionId() {
    return transactionId;
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

  @Override
  public String toString() {

    return "Transaction [date=" + transactionTime + ", tid=" + transactionId + ", price=" + price + ", amount=" + quantity + "]";
  }

}
