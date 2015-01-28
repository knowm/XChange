package com.xeiam.xchange.btce.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from BTCE
 * </p>
 */
@Deprecated
public class BTCETrade {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;
  private String tradeableIdentifier;
  private String currency;
  private String tradeType;

  /**
   * Constructor
   * 
   * @param amount
   * @param date
   * @param price
   * @param tid
   * @param tradeableIdentifier
   * @param currency
   * @param tradeType
   */
  public BTCETrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("price") BigDecimal price,
      @JsonProperty("tid") long tid, @JsonProperty("item") String tradeableIdentifier, @JsonProperty("price_currency") String currency,
      @JsonProperty("trade_type") String tradeType) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
    this.tradeableIdentifier = tradeableIdentifier;
    this.currency = currency;
    this.tradeType = tradeType;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public String getTradeableIdentifier() {

    return tradeableIdentifier;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getCurrency() {

    return currency;
  }

  public long getTid() {

    return tid;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "BTCETrade [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + ", tradeableIdentifier=" + tradeableIdentifier
        + ", currency=" + currency + ", tradeType=" + tradeType + "]";
  }

}
