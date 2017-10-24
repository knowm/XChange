package org.knowm.xchange.anx.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a buy or sell order from ANX
 */
public final class ANXOrder {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final long priceInt;
  private final long amountInt;
  private final long stamp;

  /**
   * Constructor
   *
   * @param price
   * @param amount
   * @param priceInt
   * @param amountInt
   * @param stamp
   */
  public ANXOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("price_int") long priceInt,
      @JsonProperty("amount_int") long amountInt, @JsonProperty("stamp") long stamp) {

    this.price = price;
    this.amount = amount;
    this.priceInt = priceInt;
    this.amountInt = amountInt;
    this.stamp = stamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getPriceInt() {

    return priceInt;
  }

  public long getAmountInt() {

    return amountInt;
  }

  public long getStamp() {

    return stamp;
  }

  @Override
  public String toString() {

    return "ANXOrder [price=" + price + ", amount=" + amount + ", priceInt=" + priceInt + ", amountInt=" + amountInt + ", stamp=" + stamp + "]";
  }

}
