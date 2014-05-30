package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyBuyOrder {

  private final BigDecimal buyPrice;
  private final BigDecimal quantity;
  private final BigDecimal total;

  @JsonCreator
  public CryptsyBuyOrder(@JsonProperty("buyprice") BigDecimal buyPrice, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total) {

    this.buyPrice = buyPrice;
    this.quantity = quantity;
    this.total = total;
  }

  /**
   * @return the buyPrice
   */
  public BigDecimal getBuyPrice() {

    return buyPrice;
  }

  /**
   * @return the quantity
   */
  public BigDecimal getQuantity() {

    return quantity;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {

    return total;
  }

  /**
   * @return a string representation of this object
   */
  @Override
  public String toString() {

    return "CryptsyBuyOrder [" + "'Buy Price=" + buyPrice + "'Quantity=" + quantity + "'Total=" + total + "']";
  }
}
