package org.knowm.xchange.latoken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"price": 136.3,
 * 	"amount": 7.024
 * }
 * </pre>
 *
 * @author Ezer
 */
public class PriceLevel {

  private final double price;
  private final double amount;

  /**
   * C'tor
   *
   * @param price
   * @param amount
   */
  public PriceLevel(@JsonProperty("price") double price, @JsonProperty("amount") double amount) {

    this.price = price;
    this.amount = amount;
  }

  /**
   * Price of level
   *
   * @return
   */
  public double getPrice() {
    return price;
  }

  /**
   * Total order volume on this level
   *
   * @return
   */
  public double getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "PriceLevel [price = " + price + ", amount = " + amount + "]";
  }
}
