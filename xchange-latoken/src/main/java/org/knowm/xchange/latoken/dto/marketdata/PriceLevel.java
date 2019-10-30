package org.knowm.xchange.latoken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

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

  private final BigDecimal price;
  private final BigDecimal amount;

  /**
   * C'tor
   *
   * @param price
   * @param amount
   */
  public PriceLevel(
      @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {

    this.price = price;
    this.amount = amount;
  }

  /**
   * Price of level
   *
   * @return
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Total order volume on this level
   *
   * @return
   */
  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "PriceLevel [price = " + price + ", amount = " + amount + "]";
  }
}
